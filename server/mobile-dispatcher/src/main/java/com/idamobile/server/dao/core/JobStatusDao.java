package com.idamobile.server.dao.core;

import com.idamobile.server.model.JobStatusRecord;

public interface JobStatusDao {
	
	void save(JobStatusRecord status);
	
	JobStatusRecord get(String jobId);
	
}
