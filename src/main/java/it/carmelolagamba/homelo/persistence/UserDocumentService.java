package it.carmelolagamba.homelo.persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;

import it.carmelolagamba.homelo.model.Home;
import it.carmelolagamba.homelo.model.User;
import it.carmelolagamba.mongo.filter.Filter;
import it.carmelolagamba.mongo.filter.FilterOperator;
import it.carmelolagamba.mongo.service.crud.AbstractDocumentService;
import it.carmelolagamba.mongo.utils.BasicObjectUtils;

@Component
public class UserDocumentService extends AbstractDocumentService {

	private static final String COLLECTION_NAME = "User";

	private Logger logger = LoggerFactory.getLogger(UserDocumentService.class);

	@Autowired
	private UserCollectionService userCollectionService;

	public List<User> findUsersByHome(Home home) {
		BasicDBObject filters = BasicObjectUtils.getFilterObject(new Filter("house", FilterOperator.EQUALS, home.getCode()));
				
		logger.debug("Finding all users with this filters {}", filters);
		
		return find(userCollectionService.getCollection(COLLECTION_NAME), filters);
	}
}
