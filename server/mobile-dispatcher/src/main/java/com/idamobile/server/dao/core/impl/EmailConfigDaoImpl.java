package com.idamobile.server.dao.core.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.server.dao.core.EmailConfigDao;
import com.idamobile.server.model.EmailConfig;

@Repository
public class EmailConfigDaoImpl implements EmailConfigDao{

	private static final String SQL_SELECT_EMAIL_CONFIG = 
		"SELECT email_type, recipients, subject, xslt_path " +
		"FROM ida_email_conf " +
		"WHERE email_type = ?";
	
	private static final String SQL_SELECT_ALL_EMAIL_CONFIGS = 
		"SELECT email_type, recipients, subject, xslt_path " +
		"FROM ida_email_conf ";	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = true)
	@Override
	public EmailConfig get(int emailType) {		
		return jdbcTemplate.query(SQL_SELECT_EMAIL_CONFIG, new Object[] {emailType}, new ResultSetExtractor<EmailConfig>() {

			@Override
			public EmailConfig extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return createFromResultSet(rs);
				}else {
					return EmailConfig.DEFAULT_CONFIG;
				}
			}
			
		});
	}

	@Transactional(readOnly = true)
	@Override
	public List<EmailConfig> all() {
		return jdbcTemplate.query(SQL_SELECT_ALL_EMAIL_CONFIGS, new RowMapper<EmailConfig>() {

			@Override
			public EmailConfig mapRow(ResultSet rs, int row) throws SQLException {
				return createFromResultSet(rs);
			}
			
		});
	}
	
	private EmailConfig createFromResultSet(final ResultSet rs) throws SQLException {
		return new EmailConfig(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
	}
	
}
