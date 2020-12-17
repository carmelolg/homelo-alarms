package it.carmelolagamba.homelo.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;

import it.carmelolagamba.mongo.service.crud.AbstractDocumentService;

@Component
public class TokenDocumentService extends AbstractDocumentService {

	private static final String COLLECTION_NAME = "Token";

	private Logger logger = LoggerFactory.getLogger(TokenDocumentService.class);

	@Autowired
	private TokenCollectionService tokenCollectionService;

	public boolean isTokenValid(String jwt) {
		logger.debug("User [{}]: check if token is present");

		BasicDBObject filters = new BasicDBObject("jwt", jwt);
		if (count(tokenCollectionService.getCollection(COLLECTION_NAME), filters) > 0) {
			return true;
		}

		return false;

	}

	
	public boolean isTokenPresentByUser(String user) {
		logger.debug("User [{}]: check if token is present");

		BasicDBObject filters = new BasicDBObject("user", user);
		if (count(tokenCollectionService.getCollection(COLLECTION_NAME), filters) > 0) {
			return true;
		}

		return false;

	}
}
