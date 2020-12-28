package it.carmelolagamba.homelo.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.carmelolagamba.homelo.model.Detection;
import it.carmelolagamba.homelo.model.Metric;

@Component
public class GasAlertService extends AlertService {


	@Scheduled(cron = "0/5 * * * * *")
	protected void check() {
		checkThreshold();
	}

	@Override
	protected Metric getMetric() {
		return Metric.GAS;
	}

	@Override
	protected boolean check(Detection detection) {
		return detection.getGas() >= 1000;
	}

	@Override
	protected Object getValue(Detection detection) {
		return detection.getGas();
	}
}
