package com.idamobile.server.controller;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idamobile.server.dao.core.JobStatusDao;
import com.idamobile.server.model.JobStatusRecord;
import com.idamobile.server.quartz.QuartzManager;

/**
 * Controller which is responsible for starting quartz-scheduler's jobs.
 * The following commands are available:
 * - http://hostname:port/idaserver/jobExecution/execute?className=[className @implements Job]
 * - http://hostname:port/idaserver/jobExecution/status?className=[className @implements Job]
 * @author zjor
 *
 */
@Controller
@RequestMapping("/jobExecution")
public class JobExecutionController {
	
	private final Logger log = Logger.getLogger(JobExecutionController.class);
	
	@Autowired
	private QuartzManager quartzManager;
	
	@Autowired
	private JobStatusDao statusDao;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="execute", method = RequestMethod.GET)
	public @ResponseBody boolean executeJob(@RequestParam String className) {
		boolean success = true;
		try {
			
			String jobId = className+System.currentTimeMillis();
			
			Class<? extends Job> clazz = (Class<? extends Job>) Class.forName(className);
			JobDetail jobDetail = JobBuilder.newJob(clazz)
				.withIdentity(jobId)
				.build();
			
			Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(jobId)
				.startNow()
				.build();
			
			quartzManager.getScheduler().scheduleJob(jobDetail, trigger);
			
		} catch (Exception e) {
			log.error("Failed to execute job", e);
			success = false;
			
		} 
		return success;		
		
	}
	
	@RequestMapping(value = "status", method = RequestMethod.GET)
	public @ResponseBody JobStatusRecord status(@RequestParam String className) {
		return statusDao.get(className);
		
	}

}
