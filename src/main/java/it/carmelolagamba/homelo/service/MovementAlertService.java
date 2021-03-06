package it.carmelolagamba.homelo.service;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.carmelolagamba.homelo.model.Detection;
import it.carmelolagamba.homelo.model.Metric;

@Component
public class MovementAlertService extends AlertService {

	@Scheduled(cron = "0/10 * * * * *")
	protected void check() {
		checkThreshold();
	}

	@Override
	protected Metric getMetric() {
		return Metric.MOVEMENT;
	}

	@Override
	protected boolean check(Detection detection) {
		boolean check = Minutes.minutesBetween(new DateTime(detection.getDate()), new DateTime())
				.isLessThan(Minutes.minutes(30));
		return detection.isMovement() && check;
	}

	@Override
	protected Object getValue(Detection detection) {
		return detection.isMovement() ? "Presenza avvertita." : "Nessun movimento percepito.";
	}
}
