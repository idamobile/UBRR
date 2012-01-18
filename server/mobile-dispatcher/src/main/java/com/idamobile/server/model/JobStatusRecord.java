package com.idamobile.server.model;

/**
 * Represnts status of a job in the database
 * @author zjor
 *
 */
public class JobStatusRecord {

	private static final String RUNNING_VALUE = "running";
	private static final String FINISHED_VALUE = "finished";
	private static final String FAILED_VALUE = "failed";
	
	public enum JobStatus {		
		RUNNING(RUNNING_VALUE), 
		FINISHED(FINISHED_VALUE), 
		FAILED(FAILED_VALUE);		
		
		private String value;
		
		private JobStatus(String value) {
			this.value = value;
		}
		
		public static JobStatus parse(String status) {
			if (RUNNING_VALUE.equals(status)) {
				return RUNNING;
			}else if (FINISHED_VALUE.equals(status)) {
				return FINISHED;
			}else {
				return FAILED;
			}
		}
		
		@Override
		public String toString() {
			return value;
		}
	}
	
	private String jobId;
	
	private JobStatus status;
	
	private long lastUpdateTime;

	public JobStatusRecord(String jobId, JobStatus status, long lastUpdateTime) {
		this.jobId = jobId;
		this.status = status;
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public JobStatusRecord(String jobId, String status, long lastUpdateTime) {
		this(jobId, JobStatus.parse(status), lastUpdateTime);
	}
	
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	
	
}
