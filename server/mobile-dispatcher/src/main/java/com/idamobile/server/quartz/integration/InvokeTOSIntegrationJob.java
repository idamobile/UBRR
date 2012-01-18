package com.idamobile.server.quartz.integration;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class InvokeTOSIntegrationJob implements Job{

	public static final String INTEGRATION_NAME = "int_name";
	public static final String ENDPOINT_URL = "endpoint_url";
	
	private Logger log = Logger.getLogger(InvokeTOSIntegrationJob.class);
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
	
		String name = ctx.getJobDetail().getJobDataMap().getString(INTEGRATION_NAME);
		
		log.debug("Starting integration: "+name);
		
		String url = ctx.getJobDetail().getJobDataMap().getString(ENDPOINT_URL);
		
		if (StringUtils.isEmpty(url)) {
			log.warn("Endpoint URL is empty");
			return;
		}
		
		log.debug("Executing integration for endpoint: "+url);
		
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		
		try {
			String response = EntityUtils.toString(client.execute(get).getEntity());
			log.debug("Response received: "+response);
		} catch (Exception e) {
			log.error("Error during integration invokation", e);
		} 
		
		
	}

}
