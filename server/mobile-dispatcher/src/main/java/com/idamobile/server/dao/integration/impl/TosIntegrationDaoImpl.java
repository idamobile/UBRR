package com.idamobile.server.dao.integration.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.server.dao.integration.TosIntegrationDao;
import com.idamobile.server.model.integration.TosJobConfiguration;

@Repository
public class TosIntegrationDaoImpl implements TosIntegrationDao{

	private static final String SQL_SELECT_INTEGRATIONS = 
		"SELECT integration, url, cron_schedule FROM ida_tos_integrations";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = true)
	@Override
	public List<TosJobConfiguration> get() {
		return jdbcTemplate.query(SQL_SELECT_INTEGRATIONS, new RowMapper<TosJobConfiguration>(){

			@Override
			public TosJobConfiguration mapRow(ResultSet rs, int row) throws SQLException {				
				return new TosJobConfiguration(rs.getString(1), rs.getString(2), rs.getString(3));
			}
			
		});
	}

}
