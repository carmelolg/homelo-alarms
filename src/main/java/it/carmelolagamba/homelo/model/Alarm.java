package it.carmelolagamba.homelo.model;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Alarm {

	@BsonId
	private ObjectId id;

	private String house;
	private Date sentAt;
	private boolean disabled;

	public Alarm() {
		// TODO Auto-generated constructor stub
	}

	public Alarm(String house, Date sentAt) {
		super();
		this.house = house;
		this.sentAt = sentAt;
	}
	
	public Alarm(String house, boolean active) {
		super();
		this.house = house;
		this.disabled = active;
	}
	
	public Alarm(String house, Date sentAt, boolean active) {
		super();
		this.house = house;
		this.sentAt = sentAt;
		this.disabled = active;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public Date getSentAt() {
		return sentAt;
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}
