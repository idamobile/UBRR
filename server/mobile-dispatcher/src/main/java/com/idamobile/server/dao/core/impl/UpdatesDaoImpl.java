package com.idamobile.server.dao.core.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.server.dao.core.UpdatesDao;

@Repository
public class UpdatesDaoImpl implements UpdatesDao {
	
	private static final String SQL_SELECT_TIME = 
			"SELECT last_update_time FROM ida_last_updates WHERE entity_name = ?";
	
	private static final String SQL_COUNT_TIME = 
			"SELECT COUNT(*) FROM ida_last_updates WHERE entity_name = ?";
	
	private static final String SQL_INSERT_TIME = 
			"INSERT INTO ida_last_updates (entity_name, last_update_time) VALUES (?, ?)";
	
	private static final String SQL_UPDATE_TIME = 
			"UPDATE ida_last_updates SET last_update_time = ? WHERE entity_name = ?";
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	@Override
	public long getUpdateTime(String entityName) {
		return jdbcTemplate.query(SQL_SELECT_TIME, new Object[] { entityName },
				new ResultSetExtractor<Long>() {

					@Override
					public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getLong(1);
						}
						return new Date().getTime();
					}

				});
	}

	@Transactional
	@Override
	public void saveLastUpdateTime(String entityName, long time) {
		if (jdbcTemplate.queryForInt(SQL_COUNT_TIME, new Object[]{entityName}) == 0) {
			jdbcTemplate.update(SQL_INSERT_TIME, new Object[] {entityName, time});
		}else {
			jdbcTemplate.update(SQL_UPDATE_TIME, new Object[] {time, entityName});
		}
		
	}

	@Override
	public void saveLastUpdateTimeIsNow(String entityName) {
		saveLastUpdateTime(entityName, System.currentTimeMillis());
	}

}
