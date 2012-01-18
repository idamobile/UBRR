package com.idamobile.server.quartz.integration;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import com.idamobile.server.dao.core.UpdatesDao;
import com.idamobile.server.dao.integration.TosIntegrationDao;
import com.idamobile.server.model.integration.TosJobConfiguration;
import com.idamobile.server.quartz.AbstractStatusAwareJob;
import com.idamobile.server.util.SpringApplicationContext;
import com.sun.corba.se.impl.ior.NewObjectKeyTemplateBase;

public class UpdateIntegrationsScheduleJob extends AbstractStatusAwareJob{

	private static final String PROPERTY_KEY_HOST = "server.hostname";	
	private static final String PROPERTY_KEY_PORT = "server.port";	
	private static final String PROPERTY_KEY_SCHEMA = "server.http_schema";
	
	private Logger log = Logger.getLogger(UpdateIntegrationsScheduleJob.class);
	
	private TosIntegrationDao integrationDao;
	
	public UpdateIntegrationsScheduleJob() {
		super();		
		integrationDao = (TosIntegrationDao) SpringApplicationContext.getBean("tosIntegrationDao");
	}
	
	@Override
	protected void executeJob(JobExecutionContext ctx) throws Exception {
		log.debug("Updating TOS integrations");
		
		String host = propertyDao.getProperty(PROPERTY_KEY_HOST);
		String port = propertyDao.getProperty(PROPERTY_KEY_PORT);
		String schema = propertyDao.getProperty(PROPERTY_KEY_SCHEMA);				
		
		Scheduler scheduler = ctx.getScheduler();
		
		for (TosJobConfiguration conf: integrationDao.get()) {
			JobKey key = new JobKey(conf.getName());
			scheduler.deleteJob(key);
			
			String url = String.format("%s://%s:%s/%s", schema, host, port, conf.getUrl());
			
			JobDetail jobDetail = JobBuilder.newJob(InvokeTOSIntegrationJob.class)
									.withIdentity(key)
									.usingJobData(InvokeTOSIntegrationJob.INTEGRATION_NAME, conf.getName())
									.usingJobData(InvokeTOSIntegrationJob.ENDPOINT_URL, url)
									.build();
			
			Trigger trigger = TriggerBuilder.newTrigger()
								.withIdentity(conf.getName())
								.withSchedule(CronScheduleBuilder.cronSchedule(conf.getCronSchedule()))
								.build();
			
			scheduler.scheduleJob(jobDetail, trigger);
			
			log.debug(String.format("Scheduled [%s, %s, %s]", conf.getName(), url, conf.getCronSchedule()));
			
		}		
		
	}

}
