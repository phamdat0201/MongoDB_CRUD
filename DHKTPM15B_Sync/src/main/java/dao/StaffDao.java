package dao;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

public class StaffDao extends AbstractDao{
	
	private MongoCollection<Document> staffCollection;

	public StaffDao(MongoClient client) {
		super(client);
		staffCollection = db.getCollection("staffs");
	}

}
