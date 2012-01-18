package com.idamobile.server.quartz;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.idamobile.server.dao.core.JobStatusDao;
import com.idamobile.server.dao.core.PropertyDao;
import com.idamobile.server.dao.core.UpdatesDao;
import com.idamobile.server.model.JobStatusRecord;
import com.idamobile.server.model.JobStatusRecord.JobStatus;
import com.idamobile.server.util.SpringApplicationContext;

public abstract class AbstractStatusAwareJob implements Job{

	private final Logger log = Logger.getLogger(AbstractStatusAwareJob.class);
	
	protected JdbcTemplate jdbcTemplate;
	
	protected UpdatesDao updatesDao;
	
	protected PropertyDao propertyDao;
	
	private JobStatusDao statusDao;
	
	public AbstractStatusAwareJob() {
		jdbcTemplate = (JdbcTemplate) SpringApplicationContext.getBean("jdbcTemplate");
		updatesDao = (UpdatesDao) SpringApplicationContext.getBean("updatesDao");
		propertyDao = (PropertyDao) SpringApplicationContext.getBean("propertyDao");
		statusDao = (JobStatusDao) SpringApplicationContext.getBean("jobStatusDao");		
	}
	
	public boolean isRunning() {
		JobStatusRecord statusRecord = statusDao.get(getJobId());		
		return (statusRecord != null && statusRecord.getStatus() == JobStatus.RUNNING);		
	}
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		
		if (isRunning()) {
			log.warn("The same job is already running");
			return;
		}
		
		log.debug("Start execution of the job: "+getClass().getName());		
		
		statusDao.save(new JobStatusRecord(getJobId(), JobStatus.RUNNING, System.currentTimeMillis()));
		
		JobStatus status = null;
		
		try {
			executeJob(ctx);
			status = JobStatus.FINISHED;
		}catch (Exception e) {
			log.error("Failed to execute job: "+getClass().getName(), e);
			status = JobStatus.FAILED;
		}finally {
			statusDao.save(new JobStatusRecord(getJobId(), status, System.currentTimeMillis()));			
			log.debug("Finished execution of the job: "+getClass().getName());
		}		
	}
	
	/**
	 * Returns a unique identifier of the job
	 * @return
	 */
	protected String getJobId() {
		return getClass().getName();
	}
	
	protected abstract void executeJob(JobExecutionContext ctx) throws Exception;

}
