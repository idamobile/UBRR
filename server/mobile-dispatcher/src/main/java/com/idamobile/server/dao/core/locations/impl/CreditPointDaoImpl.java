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
import com.idamobile.server.dao.core.locations.CreditPointDao;
import com.idamobile.server.model.locations.CreditPoint;
import com.idamobile.server.model.locations.GeoPoint;

@Repository
public class CreditPointDaoImpl implements CreditPointDao {

	private static final String SQL_COUNT_CREDIT_POINTS = 
		"SELECT COUNT(*) FROM " + TableNames.CREDIT_POINTS;
	
	private static final String FIELDS = "	pay_cred_id, zip_code, city, subway_station, name, address, operation_time, latitude, longitude, phone, services ";
	
	private static final String SQL_SELECT_CREDIT_POINTS = 
		"SELECT " + FIELDS +
		"FROM " + TableNames.CREDIT_POINTS +
		"	WHERE order_num >= ? AND order_num < ? " +
		"ORDER BY order_num ASC";

	private static final String SQL_SELECT_NEAREST_CREDIT_POINT = 
		"SELECT * FROM (SELECT " + FIELDS +
		"FROM " + TableNames.CREDIT_POINTS +
		" ORDER BY ida_distance(latitude, longitude, ?, ?) " +
		") src WHERE ROWNUM < 2";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	@Override
	public int count() {
		return jdbcTemplate.queryForInt(SQL_COUNT_CREDIT_POINTS);
	}
	
	private CreditPoint extract(ResultSet rs) throws SQLException {
		return new CreditPoint(
				rs.getInt(1),
				rs.getString(5), // name
				rs.getString(3), // city
				rs.getString(6), // address
				rs.getString(2), // zipcode
				rs.getString(4), // subway
				rs.getDouble(8), // lat
				rs.getDouble(9), // lng
				rs.getString(10),// phone
				rs.getString(7), // optime
				rs.getString(11) // services
			);
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<CreditPoint> get(int page, int pageSize) {

		int startIndex = (page-1) * pageSize;
		int endIndex = page*pageSize;

		return jdbcTemplate.query(SQL_SELECT_CREDIT_POINTS, new Object[] {startIndex, endIndex}, new RowMapper<CreditPoint>() {
			
			@Override
			public CreditPoint mapRow(ResultSet rs, int row) throws SQLException {
				return extract(rs);
			}
		});
	}

	@Transactional(readOnly = true)
	@Override
	public CreditPoint get(GeoPoint location) {
		return jdbcTemplate.query(SQL_SELECT_NEAREST_CREDIT_POINT, location.asArray(), new ResultSetExtractor<CreditPoint>() {
			
			@Override
			public CreditPoint extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return extract(rs);
				}else {
					return null;
				}
			}
			
		});
	}
}
