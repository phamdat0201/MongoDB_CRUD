package dao;

import org.bson.Document;

import entity.Order;

public class Mapper {
	
	public static Order orderFromDocument(Document doc) {
		
		Order order = new Order();
		
		order.setOrderID(doc.getObjectId("_id").toHexString());
		order.setOrderDate(doc.getDate("order_date"));
		
		return order;
		
	}
	
}
