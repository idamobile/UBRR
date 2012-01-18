package com.idamobile.server.dao.core.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.server.dao.core.PropertyDao;

@Repository
public class PropertyDaoImpl implements PropertyDao{

	private static final String SQL_SELECT_PROPERTY = "select value from ida_properties where name = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	@Override
	public String getProperty(String name) {		
		return jdbcTemplate.query(SQL_SELECT_PROPERTY, new Object[]{name}, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString(1);
				}
				return StringUtils.EMPTY;
			}
			
		});
	}

}
