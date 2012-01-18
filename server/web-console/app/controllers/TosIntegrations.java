package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import models.TosIntegration;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.With;
import sun.net.www.http.HttpClient;
import util.HttpUtils;

@With(Secure.class)
public class TosIntegrations extends Controller{

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }

	public static void list(String response) {
		List<TosIntegration> integrations = TosIntegration.all().fetch();
		render(integrations, response);
	}

	public static void intPopup(long id) {
		TosIntegration i = TosIntegration.findById(id);
		render(i);
	}
	
	public static void delete(long id) {
		TosIntegration i = TosIntegration.findById(id);
		if (i != null) {
			i.delete();
		}
		list(null);		
	}
	
	public static void save(TosIntegration i) {
		i.save();
		
		String requestUrl = "http://"+Http.Request.current().host+"/idaserver/jobExecution/execute?className=com.idamobile.server.quartz.integration.UpdateIntegrationsScheduleJob";
		String response = HttpUtils.get(requestUrl);		
		
		list(response);
	}	
	
	public static void executeIntegration(long integrationId) {
		TosIntegration i = TosIntegration.findById(integrationId);
		String intUrl = "http://"+Http.Request.current().host+"/"+i.url;
		
		String data = HttpUtils.get(intUrl);
			
		if (data.indexOf(">0<") > 0) {
			data = "Success";
		}
					
		render(data);
	}
}
