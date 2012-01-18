package controllers;

import java.util.List;

import models.custom.configuration.ProductStatusMapping;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class ProductStatuses extends Controller{

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }
	
	public static void list() {
		List<ProductStatusMapping> mappings = ProductStatusMapping.all().fetch();		
		render(mappings);
	}
	
	public static void statusPopup(long id) {
		ProductStatusMapping mapping = ProductStatusMapping.findById(id);
		render(mapping);
	}
		
	public static void save(ProductStatusMapping mapping) {
		mapping.save();		
		list();
	}
	
	public static void delete(long id) {
		ProductStatusMapping mapping = ProductStatusMapping.findById(id);
		if (mapping!= null) {
			mapping.delete();
		}
		list();
	}
	
}
