package app;

import java.util.Arrays;
import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import dao.OrderDao;
import dao.ProductDao;
import entity.Order;
import entity.Product;

public class App {
	public static void main(String[] args) {
		
		MongoClient client = MongoClients.create();
		ProductDao productDao = new ProductDao(client );
		
		OrderDao orderDao = new OrderDao(client);
		
		productDao.getProductsMaxPrice()
		.forEach(pp -> System.out.println(pp));
		
//		List<Product> ps = productDao.getProducts("\"Trek Powerfly\"");
//		ps.forEach(p -> System.out.println(p.getName()));
		
//		long n = orderDao.updateOrdersByOrderDate();
//		System.out.println(n);
//		
//		Order or = orderDao.getOrder("615279c5dc90aa2be71fd90c");
//		
//		System.out.println(or);
		
//		Product p1 = productDao.getProdcut(3l);
//		System.out.println(p1);
		
//		List<Product> products = Arrays.asList(
//					new Product(12122l, "sdf", "sdf", "gfd", Arrays.asList("red","blue"), 2021, 100),
//					new Product(12125l, "sdf", "sdf", "gfd", Arrays.asList("red","blue"), 2021, 100)
//				);
//		long n = productDao.addProducts(products);
//		System.out.println(n);
		
//		productDao
//		.getProductsNew()
//		.forEach(p-> System.out.println(p));
		
	}
}
