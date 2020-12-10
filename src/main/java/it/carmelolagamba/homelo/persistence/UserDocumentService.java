package it.carmelolagamba.homelo.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.carmelolagamba.mongo.service.crud.AbstractDocumentService;

@Component
public class UserDocumentService extends AbstractDocumentService {

	private static final String COLLECTION_NAME = "User";

	private Logger logger = LoggerFactory.getLogger(UserDocumentService.class);

	@Autowired
	private UserCollectionService userCollectionService;
}
