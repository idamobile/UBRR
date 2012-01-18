package controllers;

import play.*;
import play.db.jpa.Blob;
import play.i18n.Lang;
import play.mvc.*;

import java.net.SecureCacheResponse;
import java.util.*;

import models.*;
import models.core.Contact;
import models.core.Image;
import models.core.Image.Platform;
import models.core.Image.Resolution;

@With(Secure.class)
public class Application extends Controller {
	
	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }
	
	public static void changeLanguage(String lang) {
		Lang.change(lang);
		index();
	}
	
    public static void index() {
        render();
    }
    
    public static void edit() {
    	render();
    }
    
    public static void saveContacts() {
    	Map<String, String> contacts = new HashMap<String, String>();
    	for (String key: params.all().keySet()) {
    		if (key.startsWith(Contact.PREFIX)) {
    			contacts.put(key, params.get(key));
    		}
    	}    	
    	models.Contacts.saveContacts(contacts);
    	index();
    }
     
}