package it.carmelolagamba.homelo.persistence;

import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;

import it.carmelolagamba.homelo.model.Alarm;
import it.carmelolagamba.mongo.service.crud.AbstractDocumentService;

@Component
public class AlarmDocumentService extends AbstractDocumentService {

	private static final String COLLECTION_NAME = "Alarm";

	private Logger logger = LoggerFactory.getLogger(AlarmDocumentService.class);

	@Autowired
	private AlarmCollectionService alarmCollectionService;

	public List<Alarm> findAll() {
		logger.debug("Find all home triggered");
		return find(alarmCollectionService.getCollection(COLLECTION_NAME), new BasicDBObject());
	}

	public Document insert(Alarm alarm) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("{");
		
		if(alarm.getHouse() != null) {
			builder.append("\"house\": \"" + alarm.getHouse()+ "\",");
		}
		
		if(alarm.getSentAt() != null) {
			builder.append("\"sentAt\": { \"$date\": " + alarm.getSentAt().getTime() + "}},");
		}
		
		builder.append("\"active\": " + alarm.isActive());
		
		builder.append("}");
		
		return insert(COLLECTION_NAME, builder.toString());
	}
	
	public void removeAll(String house) {
		logger.debug("Enable all alarm removing all data for house {}", house);
		removeByFilters(COLLECTION_NAME, new BasicDBObject("house", house));
	}
}
