package com.idamobile.server.quartz;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.Lifecycle;


/**
 * Class is responsible for starting and stopping Quartz scheduler according to
 * the application state
 * 
 * @author zjor
 * 
 */

public class QuartzManager implements Lifecycle {

	private final Logger log = Logger.getLogger(QuartzManager.class);
	
	private boolean started = false;
	private Scheduler scheduler;
	
	@Override
	public boolean isRunning() {
		return started;
	}

	@Override
	public void start() {
		started = true;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();					
		} catch (SchedulerException e) {
			log.error("Unable to start Quartz Scheduler", e);
		}
	}

	@Override
	public void stop() {
		started = false;
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			log.error("Unable to stop Quartz Scheduler", e);
		}

	}

	public Scheduler getScheduler() {
		return scheduler;
	}
}
