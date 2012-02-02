package controllers;

import java.util.List;

import models.custom.configuration.ProductStatusMapping;
import models.ubrr.Product;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Products extends Controller{

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }
	
	public static void list() {
		List<Product> products = Product.all().fetch();		
		render(products);
	}
	
	public static void statusPopup(String id) {
		render();
	}
		
	public static void save(Product product) {
		product.save();		
		list();
	}
	
	public static void delete(String id) {
		Product product = Product.findById(id);
		if (product!= null) {
			product.delete();
		}
		list();
	}
	
}
