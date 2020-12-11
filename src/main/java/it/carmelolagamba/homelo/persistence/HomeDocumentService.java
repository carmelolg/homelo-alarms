package it.carmelolagamba.homelo.persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;

import it.carmelolagamba.homelo.model.Home;
import it.carmelolagamba.mongo.service.crud.AbstractDocumentService;

@Component
public class HomeDocumentService extends AbstractDocumentService {

	private static final String COLLECTION_NAME = "Home";

	private Logger logger = LoggerFactory.getLogger(HomeDocumentService.class);

	@Autowired
	private HomeCollectionService homeCollectionService;

	public List<Home> findAll() {
		logger.debug("Find all home triggered");
		return find(homeCollectionService.getCollection(COLLECTION_NAME), new BasicDBObject());
	}
}
