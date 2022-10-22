package dao;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertManyResult;

import entity.Product;

public class ProductDao extends AbstractDao{
	
	private MongoCollection<Product> productCollection;

	public ProductDao(MongoClient client) {
		super(client);
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		productCollection = db.getCollection("products", Product.class).withCodecRegistry(pojoCodecRegistry);
	}
	
//	BikeStores> db.products.createIndex({product_name:"text"})
	public List<Product> getProducts(String keywords) {
		List<Product> products = new ArrayList<Product>();
		
		productCollection
		.find(Filters.text(keywords))
		.iterator()
		.forEachRemaining(products::add);
		
		return products;
	}
	
	
	public long addProducts(List<Product> products) {
		
		InsertManyResult rs = productCollection.insertMany(products);
		
		return rs.getInsertedIds().size();
	}
	
	public Product getProdcut(long id) {
		
		return productCollection.find(Filters.eq("_id", id)).first();
		
	}
	
//	 db.orders.createIndex({'order_details.product_id':1})
//	BikeStores> db.products.aggregate([{ $lookup: { from: 'orders', localField: '_id', foreignField: 'order_details.product_id', as: 'rs' } },
//	{$match:{$expr:{$eq:[{$size:'$rs'},0]}}}, 
//	{$unset:'rs'}])
	public List<Product> getProductsNew() {
		List<Product> products = new ArrayList<Product>();
		Document lk = Document.parse("{ $lookup: { from: 'orders', localField: '_id', foreignField: 'order_details.product_id', as: 'rs' } }");
		Document mt = Document.parse("{$match:{$expr:{$eq:[{$size:'$rs'},0]}}}");
		Document us = Document.parse("{$unset:'rs'}");
		
		productCollection
		.aggregate(Arrays.asList(lk, mt, us))
		.iterator()
		.forEachRemaining(products::add);
		
		return products;
	}
	
//	BikeStores> db.products.aggregate([{ $group: { _id: null, ps: { $addToSet: '$$ROOT' }, maxPrice: { $max: '$price' } } }, 
	//	{ $unwind: '$ps' }, 
	//	{ $match: { $expr: { $eq: ['$ps.price', '$maxPrice'] } } },
	//	{$replaceWith:'$ps'}])
	public List<Product> getProductsMaxPrice() {
		List<Product> products = new ArrayList<Product>();
		Document gr = Document.parse("{ $group: { _id: null, ps: { $addToSet: '$$ROOT' }, maxPrice: { $max: '$price' } } }");
		Document uw = Document.parse("{ $unwind: '$ps' }");
		Document mt = Document.parse("{ $match: { $expr: { $eq: ['$ps.price', '$maxPrice'] } } }");
		Document rp = Document.parse("{$replaceWith:'$ps'}");
		
		productCollection.aggregate(Arrays.asList(gr, uw,mt, rp))
		.iterator()
		.forEachRemaining(products::add);
		
		return products;
	}

}
