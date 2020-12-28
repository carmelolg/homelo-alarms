package it.carmelolagamba.homelo.dto;

import java.util.Date;

import it.carmelolagamba.homelo.model.Metric;

public class CheckDetails {

	private String room;
	private Date date;
	private Metric metric;
	private Object currentValue;

	public CheckDetails() {
	}

	public CheckDetails(String room, Date date, Metric metric, int currentValue) {
		super();
		this.room = room;
		this.date = date;
		this.metric = metric;
		this.currentValue = currentValue;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	public Object getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Object currentValue) {
		this.currentValue = currentValue;
	}

}
