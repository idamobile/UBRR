package controllers;

import java.util.Date;
import java.util.List;

import models.Banner;
import models.core.LastUpdate;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Locations extends Controller {

	private static final String ENTITY_ATM = "locations.atms";
	private static final String ENTITY_OFFICES = "locations.offices";
	private static final String ENTITY_CREDITS = "locations.credit.points";

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }
	
	public static void list() {
		render();
	}
	
	public static String getLastUpdateForAtms() {
		return new Date(LastUpdate.get(ENTITY_ATM)).toString();
	}
	
	public static void updateAtms() {
		LastUpdate.setNow(ENTITY_ATM);
		list();
	}
	public static String getLastUpdateForOffices() {
		return new Date(LastUpdate.get(ENTITY_OFFICES)).toString();
	}
	
	public static void updateOffices() {
		LastUpdate.setNow(ENTITY_OFFICES);
		list();
	}
	public static String getLastUpdateForCredits() {
		return new Date(LastUpdate.get(ENTITY_CREDITS)).toString();
	}
	
	public static void updateCredits() {
		LastUpdate.setNow(ENTITY_CREDITS);
		list();
	}
}
