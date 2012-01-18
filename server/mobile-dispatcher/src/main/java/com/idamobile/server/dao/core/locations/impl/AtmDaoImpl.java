package com.idamobile.server.dao.core.locations.impl;

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

import com.idamobile.server.dao.core.impl.TableNames;
import com.idamobile.server.dao.core.locations.AtmDao;
import com.idamobile.server.model.locations.ATM;
import com.idamobile.server.model.locations.GeoPoint;

@Repository
public class AtmDaoImpl implements AtmDao{

	private static final String SQL_COUNT_ATMS = 
		"SELECT COUNT(*) FROM " + TableNames.ATMS;
	
	private static final String FIELDS = "	atm_id, city, subway_station, address, latitude, longitude, operation_time, services, name "; 
	
	private static final String SQL_SELECT_ATMS = 
		"SELECT " + FIELDS +
		"FROM " + TableNames.ATMS +
		"	WHERE order_num >= ? AND order_num < ? " +
		"ORDER BY order_num ASC";
	
	private static final String SQL_SELECT_NEAREST_ATM = 
		"SELECT * FROM (SELECT " + FIELDS +
		"FROM " + TableNames.ATMS +
		" ORDER BY ida_distance(latitude, longitude, ?, ?) " +
		") src WHERE ROWNUM < 2";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = true)
	@Override
	public int count() {
		return jdbcTemplate.queryForInt(SQL_COUNT_ATMS);
	}

	private ATM extract(ResultSet rs) throws SQLException {
		return new ATM(
				rs.getInt(1),
				rs.getString(9), //name
				rs.getString(2), //city
				rs.getString(4), //address
				rs.getString(3), //subway
				rs.getDouble(5), //lat
				rs.getDouble(6), //lng
				rs.getString(7), //optime
				rs.getString(8)  //services
			);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<ATM> get(int page, int pageSize) {
		int startIndex = (page-1) * pageSize;
		int endIndex = page*pageSize;
		
		return jdbcTemplate.query(SQL_SELECT_ATMS, new Object[] {startIndex, endIndex}, new RowMapper<ATM>() {

			@Override
			public ATM mapRow(ResultSet rs, int row) throws SQLException {
				return extract(rs);
			}
			
		});
	}
	
	@Transactional(readOnly = true)
	@Override
	public ATM get(GeoPoint location) {
		return jdbcTemplate.query(SQL_SELECT_NEAREST_ATM, location.asArray(), new ResultSetExtractor<ATM>() {

			@Override
			public ATM extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return extract(rs);
				}else {
					return null;
				}
			}
			
		});
	}

}
