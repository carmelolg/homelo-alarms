package it.carmelolagamba.homelo.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.carmelolagamba.homelo.model.Alarm;
import it.carmelolagamba.homelo.persistence.AlarmDocumentService;

@Component
public class AlarmService {

	private Logger logger = LoggerFactory.getLogger(AlarmService.class);

	@Autowired
	private AlarmDocumentService alarmDocumentService;

	public boolean isActive(String house) {
		Alarm alarm = alarmDocumentService.findByHouse(house);
		return alarm == null || alarm.isDisabled() == false;
	}
	
	public boolean disable(Alarm alarm) {
		logger.debug("Disable alarms for {} at {}", alarm.getHouse(), new Date());
		return alarmDocumentService.insert(alarm) != null;
	}
	
	public void enable(String house) {
		logger.debug("Enable alarms for {} at {}", house, new Date());
		alarmDocumentService.removeAll(house);
	}
}
