package it.carmelolagamba.homelo.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.carmelolagamba.homelo.dto.CheckDetails;
import it.carmelolagamba.homelo.dto.CheckInfo;
import it.carmelolagamba.homelo.model.Alarm;
import it.carmelolagamba.homelo.model.Detection;
import it.carmelolagamba.homelo.model.Home;
import it.carmelolagamba.homelo.model.Metric;
import it.carmelolagamba.homelo.model.User;
import it.carmelolagamba.homelo.persistence.AlarmDocumentService;
import it.carmelolagamba.homelo.persistence.DetectionDocumentService;
import it.carmelolagamba.homelo.persistence.HomeDocumentService;
import it.carmelolagamba.homelo.persistence.UserDocumentService;

@Component
public class GasAlertService {

	private Logger logger = LoggerFactory.getLogger(GasAlertService.class);

	@Autowired
	private DetectionDocumentService detectionDocumentService;

	@Autowired
	private HomeDocumentService homeDocumentService;

	@Autowired
	private UserDocumentService userDocumentService;

	@Autowired
	private AlarmDocumentService alarmDocumentService;

	@Autowired
	private MailService mailService;

	@Scheduled(cron = "0/5 * * * * *")
	private void checkThreshold() {
		
		List<Home> homes = homeDocumentService.findAll();
		List<Alarm> alertsInPending = alarmDocumentService.findAll();

		for (Home home : homes) {

			if (alertsInPending.stream().filter(alert -> alert.getHouse().equals(home.getCode())).count() > 0) {
				continue;
			}

			CheckInfo checkInfo = new CheckInfo();
			checkInfo.setHome(home.getName());

			// Per ogni stanza controllo gli allarmi in corso
			for (String room : home.getRooms()) {

				CheckDetails infoDetails = new CheckDetails();
				infoDetails.setMetric(Metric.GAS);
				infoDetails.setRoom(room);

				Detection current = detectionDocumentService.findLastDetection(home, room, Metric.GAS.getValue());

				if (current != null && current.getGas() >= 1000) {

					logger.info("Alarm in pending for house {} in room {}. Gas value {}", home.getCode(), room, current.getGas());
					
					infoDetails.setCurrentValue(current.getGas());
					infoDetails.setDate(current.getDate());
					checkInfo.addInfo(infoDetails);
				}

			}

			// Se c'è qualche allarme spedisco email
			if (checkInfo.getInfo().size() > 0) {

				List<User> users = userDocumentService.findUsersByHome(home);

				if (users.size() > 0) {
					// Send email to this group of users
					logger.debug("Send email to {} alarm active on {}", users.stream().map(u -> u.getUser()),
							home.getName());
					mailService.send(checkInfo, users.stream().filter(u -> u.getEmail() != null).map(u -> u.getEmail())
							.collect(Collectors.toList()));
					alarmDocumentService.insert(new Alarm(home.getCode(), new Date()));
				}

			}

		}

	}
}
