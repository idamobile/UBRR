package com.idamobile.server.dao.core.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.server.dao.core.ProductDao;

@Repository
public class ProductDaoImpl implements ProductDao {

	private static final String SQL_SELECT_PRODUCTS = 
		"select card_name from ida_card_products";
		
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = true)
	@Override
	public List<String> all() {
		return jdbcTemplate.query(SQL_SELECT_PRODUCTS, new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int row) throws SQLException {
				return rs.getString(1);
			}
			
		});
	}

}
