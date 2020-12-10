package it.carmelolagamba.homelo.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import it.carmelolagamba.homelo.model.Token;
import it.carmelolagamba.mongo.service.DatabaseService;

@Component
public class TokenCollectionService {

	@Autowired
	private DatabaseService dbService;

	public MongoCollection<Token> getCollection(String collectionName) {
		MongoDatabase db = dbService.getDefaultDb();
		return db.getCollection(collectionName, Token.class);
	}
	
	public com.mongodb.async.client.MongoCollection<Token> getAsyncCollection(String collectionName) {
		com.mongodb.async.client.MongoDatabase db = dbService.getAsyncDefaultDb();
		return db.getCollection(collectionName, Token.class);
	}
}
