package it.carmelolagamba.homelo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.carmelolagamba.homelo.config.ThresholdProperties;
import it.carmelolagamba.homelo.model.Detection;
import it.carmelolagamba.homelo.model.Metric;

@Component
@EnableConfigurationProperties({ ThresholdProperties.class })
public class GasAlertService extends AlertService {

	@Autowired
	private ThresholdProperties thresholdProperties;

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
		return detection.getGas() >= thresholdProperties.getGas();
	}

	@Override
	protected Object getValue(Detection detection) {
		return detection.getGas();
	}
}
