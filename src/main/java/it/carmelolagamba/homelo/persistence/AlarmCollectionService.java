package it.carmelolagamba.homelo.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import it.carmelolagamba.homelo.model.Alarm;
import it.carmelolagamba.mongo.service.DatabaseService;

@Component
public class AlarmCollectionService {

	@Autowired
	private DatabaseService dbService;

	public MongoCollection<Alarm> getCollection(String collectionName) {
		MongoDatabase db = dbService.getDefaultDb();
		return db.getCollection(collectionName, Alarm.class);
	}
	
	public com.mongodb.async.client.MongoCollection<Alarm> getAsyncCollection(String collectionName) {
		com.mongodb.async.client.MongoDatabase db = dbService.getAsyncDefaultDb();
		return db.getCollection(collectionName, Alarm.class);
	}
}
