package dao;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import entity.Customer;

public class CustomerDao extends AbstractDao{

	private MongoCollection<Customer> customerCollection;
	
	public CustomerDao(MongoClient client) {
		super(client);
		
		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		customerCollection = db.getCollection("customers", Customer.class).withCodecRegistry(pojoCodecRegistry);
		
	}

}
