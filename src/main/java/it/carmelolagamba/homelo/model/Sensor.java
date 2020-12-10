package it.carmelolagamba.homelo.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Sensor {

	@BsonId
	private ObjectId id;

	private String name, room, house;
	private Boolean alive, camera, gas, humidity, motion, temperature;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public Boolean getAlive() {
		return alive;
	}

	public void setAlive(Boolean alive) {
		this.alive = alive;
	}

	public Boolean getCamera() {
		return camera;
	}

	public void setCamera(Boolean camera) {
		this.camera = camera;
	}

	public Boolean getGas() {
		return gas;
	}

	public void setGas(Boolean gas) {
		this.gas = gas;
	}

	public Boolean getHumidity() {
		return humidity;
	}

	public void setHumidity(Boolean humidity) {
		this.humidity = humidity;
	}

	public Boolean getMotion() {
		return motion;
	}

	public void setMotion(Boolean motion) {
		this.motion = motion;
	}

	public Boolean getTemperature() {
		return temperature;
	}

	public void setTemperature(Boolean temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Name[" + this.name + "] ");
		builder.append("Room[" + this.room + "] ");
		builder.append("House[" + this.house + "] ");
		return builder.toString();
	}
}
