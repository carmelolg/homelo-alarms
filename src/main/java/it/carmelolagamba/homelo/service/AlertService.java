package it.carmelolagamba.homelo.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

public abstract class AlertService {

	Logger logger = LoggerFactory.getLogger(AlertService.class);
	
	@Autowired
	private HomeDocumentService homeDocumentService;
	
	@Autowired
	private AlarmDocumentService alarmDocumentService;
	
	@Autowired
	private DetectionDocumentService detectionDocumentService;
	
	@Autowired
	private UserDocumentService userDocumentService;
	
	@Autowired
	private MailService mailService;
	
	protected void checkThreshold() {
		
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
				infoDetails.setMetric(getMetric());
				infoDetails.setRoom(room);

				Detection current = detectionDocumentService.findLastDetection(home, room, getMetric().getValue());

				if (current != null && check(current)) {

					logger.info("Alarm in pending for house {} in room {}. Metric {} Value {}", home.getCode(), room, getMetric().getValue(), getValue(current));
					
					infoDetails.setCurrentValue(getValue(current));
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

	
	protected abstract Metric getMetric();
	protected abstract boolean check(Detection detection);
	protected abstract Object getValue(Detection detection);
}
