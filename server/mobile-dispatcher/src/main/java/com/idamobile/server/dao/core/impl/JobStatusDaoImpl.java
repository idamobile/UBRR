package com.idamobile.server.dao.core.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.server.dao.core.JobStatusDao;
import com.idamobile.server.model.JobStatusRecord;

@Repository
public class JobStatusDaoImpl implements JobStatusDao{

	private static final String SQL_SELECT_JOB_STATUS = "select job_id, status, last_update_time from ida_job_statuses where job_id = ?";
	private static final String SQL_COUNT_JOB_STATUSES = "select count(*) from ida_job_statuses where job_id = ?";
	private static final String SQL_INSERT_JOB_STATUS = "insert into ida_job_statuses (job_id, status, last_update_time) values (?, ?, ?)";
	private static final String SQL_UPDATE_JOB_STATUS = "update ida_job_statuses set status = ?, last_update_time = ? where job_id = ?";		
	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
	
	@Transactional
	@Override
	public void save(JobStatusRecord status) {
		if (jdbcTemplate.queryForInt(SQL_COUNT_JOB_STATUSES, new Object[]{status.getJobId()}) == 0) {
			jdbcTemplate.update(SQL_INSERT_JOB_STATUS, new Object[] {status.getJobId(), status.getStatus().toString(), status.getLastUpdateTime()});
		}else {
			jdbcTemplate.update(SQL_UPDATE_JOB_STATUS, new Object[] {status.getStatus().toString(), status.getLastUpdateTime(), status.getJobId()});
		}
	}

	@Transactional(readOnly = true)
	@Override
	public JobStatusRecord get(String jobId) {
		return jdbcTemplate.query(SQL_SELECT_JOB_STATUS, new Object[] {jobId}, new ResultSetExtractor<JobStatusRecord>() {

			@Override
			public JobStatusRecord extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if (rs.next()) {
					return new JobStatusRecord(rs.getString(1), rs.getString(2), rs.getLong(3));
				}
				return null;
			}
			
		});
	}

}
