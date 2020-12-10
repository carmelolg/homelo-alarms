package it.carmelolagamba.homelo.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.carmelolagamba.homelo.model.Sensor;
import it.carmelolagamba.homelo.persistence.SensorDocumentService;
import it.carmelolagamba.homelo.persistence.TokenDocumentService;

@Component
public class AliveService {

	private Logger logger = LoggerFactory.getLogger(AliveService.class);
	
	@Autowired
	private SensorDocumentService sensorDocumentService;
	
	@Autowired
	private TokenDocumentService tokenDocumentService;
	
	@Scheduled(cron = "0 0/15 * * * *")
	public void aliveCheck() {
		
		List<Sensor> sensors = sensorDocumentService.findAll();
		
		for (Sensor sensor : sensors) {
			boolean check = tokenDocumentService.isTokenPresentByUser(sensor.getName());
			sensor.setAlive(check);
			try {
				sensorDocumentService.update(sensor);
			} catch (InterruptedException | ExecutionException e) {
				logger.error("Update del sensore {} non andato a buon fine", sensor.getName());
			}
		}
		
	}
	
	
}
