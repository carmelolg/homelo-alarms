package it.carmelolagamba.homelo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "threshold", ignoreUnknownFields = false)
public class ThresholdProperties {

	private int gas;

	public int getGas() {
		return gas;
	}

	public void setGas(int gas) {
		this.gas = gas;
	}

}
