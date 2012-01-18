package controllers;

import java.util.List;

import models.EmailConfig;
import models.custom.configuration.BeneficiaryMapping;
import models.custom.configuration.ProductStatusMapping;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class EmailConfigs extends Controller{

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }

	public static void list() {
		List<EmailConfig> ecs = EmailConfig.all().fetch();		
		render(ecs);
	}

	public static void emailPopup(long id) {
		EmailConfig config = EmailConfig.findById(id);
		render(config);
	}

	public static void save(EmailConfig config) {
		config.save();		
		list();
	}
	
	public static void delete(long id) {
		EmailConfig config = EmailConfig.findById(id);
		if (config!= null) {
			config.delete();
		}
		list();
	}

	
	
}
