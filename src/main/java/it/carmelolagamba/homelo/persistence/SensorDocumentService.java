package it.carmelolagamba.homelo.persistence;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.async.SingleResultCallback;

import it.carmelolagamba.homelo.model.Sensor;
import it.carmelolagamba.mongo.service.MongoService;
import it.carmelolagamba.mongo.service.crud.AbstractDocumentService;

@Component
public class SensorDocumentService extends AbstractDocumentService {

	private static final String COLLECTION_NAME = "Sensor";

	private Logger logger = LoggerFactory.getLogger(SensorDocumentService.class);

	@Autowired
	private SensorCollectionService sensorCollectionService;

	@Autowired
	private MongoService mongoService;

	public List<Sensor> findAll() {
		logger.debug("Find all sensor triggered");
		return find(sensorCollectionService.getCollection(COLLECTION_NAME), new BasicDBObject());
	}

	/**
	 * Update a pre-existent sensor in MongoDB
	 * 
	 * @param sensor the sensor to update
	 * @return <code>true</code> if the sensor is updated, <code>false</code>
	 *         otherwise
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean update(Sensor sensor) throws InterruptedException, ExecutionException {

		final CompletableFuture<Boolean> future = new CompletableFuture<>();

		mongoService.getAsyncMongoClient()
				.startSession(new SingleResultCallback<com.mongodb.async.client.ClientSession>() {

					@Override
					public void onResult(com.mongodb.async.client.ClientSession session, Throwable t) {
						try {

							if (t != null) {
								logger.error("SensorDocumentService: errore nel update", t);
								future.complete(false);
							}

							session.startTransaction();
							asyncUpdate(sensorCollectionService.getAsyncCollection(COLLECTION_NAME),
									new BasicDBObject("name", sensor.getName()), new BasicDBObject("$set", sensor));

							session.commitTransaction(new SingleResultCallback<Void>() {
								@Override
								public void onResult(Void result, Throwable t) {
									future.complete((t == null) ? true : false);
								}
							});
						} catch (InterruptedException | ExecutionException e) {
							session.abortTransaction(new SingleResultCallback<Void>() {
								@Override
								public void onResult(Void result, Throwable t) {
									logger.error("SensorDocumentService: errore nel update", e);
									future.complete(false);
								}
							});
						}

					}
				});

		return future.get();
	}
}
