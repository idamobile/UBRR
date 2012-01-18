package controllers;

import models.core.Property;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Settings extends Controller{

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }

	public static void index() {
		render();
	}
	
	public static void edit() {
		render();
	}
	
	public static void save() {
		for (String key: params.all().keySet()) {
			if (key.startsWith(Property.PROPERTY_PREFIX)) {
				Property.set(key, params.get(key));
			}
		}
		index();
	}
}
