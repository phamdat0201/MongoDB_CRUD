package dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public abstract class AbstractDao {
	
	private static final String MY_DB = "BikeStores";
	private MongoClient client;
	protected MongoDatabase db;

	public AbstractDao(MongoClient client) {
		super();
		this.client = client;
		this.db = client.getDatabase(MY_DB);
	}
	
	public MongoClient getClient() {
		return client;
	}
	
	public MongoDatabase getDb() {
		return db;
	}
	
	
}
