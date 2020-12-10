package it.carmelolagamba.homelo.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import it.carmelolagamba.homelo.model.Detection;
import it.carmelolagamba.mongo.service.DatabaseService;

@Component
public class DetectionCollectionService {

	@Autowired
	private DatabaseService dbService;

	public MongoCollection<Detection> getCollection(String collectionName) {
		MongoDatabase db = dbService.getDefaultDb();
		return db.getCollection(collectionName, Detection.class);
	}
	
	public com.mongodb.async.client.MongoCollection<Detection> getAsyncCollection(String collectionName) {
		com.mongodb.async.client.MongoDatabase db = dbService.getAsyncDefaultDb();
		return db.getCollection(collectionName, Detection.class);
	}
}
