package it.carmelolagamba.homelo.model;

public enum Metric {

	GAS("gas"), TEMPERATURE("temperature"), HUMIDITY("humidity"), MOVEMENT("movement");

	String value;

	private Metric(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
