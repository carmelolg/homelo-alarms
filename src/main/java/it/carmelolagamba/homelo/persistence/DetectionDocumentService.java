package it.carmelolagamba.homelo.persistence;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;

import it.carmelolagamba.homelo.model.Detection;
import it.carmelolagamba.homelo.model.Home;
import it.carmelolagamba.mongo.filter.Filter;
import it.carmelolagamba.mongo.filter.FilterOperator;
import it.carmelolagamba.mongo.service.crud.AbstractDocumentService;
import it.carmelolagamba.mongo.utils.BasicObjectUtils;

@Component
public class DetectionDocumentService extends AbstractDocumentService {

	private static final String COLLECTION_NAME = "Detection";
	
	private Logger logger = LoggerFactory.getLogger(DetectionDocumentService.class);
	
	@Autowired
	private DetectionCollectionService detectionCollectionService;
	
	public Detection findLastDetection(Home home, String room, String metric) {
		
		BasicDBObject houseFilter = BasicObjectUtils.getFilterObject(new Filter("house", FilterOperator.EQUALS, home.getCode()));
		BasicDBObject roomFilter = BasicObjectUtils.getFilterObject(new Filter("room", FilterOperator.EQUALS, room), houseFilter);
		BasicDBObject filters = BasicObjectUtils.getFilterObject(new Filter(metric, FilterOperator.EXISTS, true), roomFilter);
		
		HashMap<String, Object> sortFilters = new HashMap<String, Object>();
		sortFilters.put("_id", -1);
		
		logger.debug("Finding last detection with this filters {}", filters);
		
		return findOne(detectionCollectionService.getCollection(COLLECTION_NAME), filters, sortFilters);
	}
	
	public static void main(String[] args) {
		DetectionDocumentService d =  new DetectionDocumentService();
		d.findLastDetection(new Home(), "Kitchen", "gas");
		
//		Date currentDate = new Date(System.currentTimeMillis());
//		
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(currentDate);
//		calendar.add(Calendar.MINUTE, -5);
//		calendar.add(Calendar.HOUR_OF_DAY, -12);
//		Date yesterdayFromCurrentDate = calendar.getTime();
//
//		HashMap<String, Object> filters = new HashMap<>();
//		for (Pair<String, Object> pair : filterList) {
//			filters.put(pair.getLeft(), pair.getRight());
//		}
//
//		filters.put("data",
//				BasicDBObjectBuilder.start("$gte", yesterdayFromCurrentDate).add("$lte", currentDate).get());

	}
}
