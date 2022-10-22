package dao;

import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;

import entity.Order;

public class OrderDao extends AbstractDao{
	
	private MongoCollection<Document> orderCollection;

	public OrderDao(MongoClient client) {
		super(client);
		orderCollection = db.getCollection("orders");
	}
	


	//order_date = shipped_date
//	BikeStores> db.orders.updateMany({},[{$set:{order_date:'$shipped_date'}}]) 
	public long updateOrdersByOrderDate() {
		
		UpdateResult rs = orderCollection.updateMany(Document.parse("{}"), 
					Arrays.asList(Filters.eq("$set", Filters.eq("order_date", "$shipped_date")))
				);
		
		return rs.getModifiedCount();
	}
	
	public Order getOrder(String id) {
		
		Order order = new Order();
		
		 Document doc = orderCollection.find(Filters.eq("_id", new ObjectId(id))).first();
		
		 order = Mapper.orderFromDocument(doc);
		 
		return order;
		
	}
}
