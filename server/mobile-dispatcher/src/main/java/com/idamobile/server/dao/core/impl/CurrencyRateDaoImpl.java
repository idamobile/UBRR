package com.idamobile.server.dao.core.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.protocol.ubrr.Currency.CurrencyOperation;
import com.idamobile.server.dao.core.CurrencyRateDao;
import com.idamobile.server.model.CurrencyRate;

@Repository
public class CurrencyRateDaoImpl implements CurrencyRateDao{

	private Logger log = Logger.getLogger(CurrencyRateDaoImpl.class);
	
	private static final String SQL_SELECT_RATES = 
		"SELECT operation, currency_code, price, amount, delta " +
		"FROM " + TableNames.CURRENCY_RATES;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = true)
	@Override
	public List<CurrencyRate> all() {
		return jdbcTemplate.query(SQL_SELECT_RATES, new RowMapper<CurrencyRate>() {

			@Override
			public CurrencyRate mapRow(ResultSet rs, int row) throws SQLException {
				CurrencyOperation operation = CurrencyOperation.valueOf(rs.getInt(1));
				if (operation == null) {
					log.warn("Unknown currency operation type: "+rs.getInt(1));
				}
								
				return new CurrencyRate(operation, rs.getInt(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5));
			}
			
		});
	}

}
