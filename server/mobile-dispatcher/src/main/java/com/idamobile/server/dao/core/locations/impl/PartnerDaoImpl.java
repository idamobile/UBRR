package com.idamobile.server.dao.core.locations.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.server.dao.core.impl.TableNames;
import com.idamobile.server.dao.core.locations.PartnerDao;
import com.idamobile.server.model.locations.GeoPoint;
import com.idamobile.server.model.locations.Partner;

@Repository
public class PartnerDaoImpl implements PartnerDao{

	private static final String FIELDS = "partner_id, zip_code, city, subway_station, name, address, operation_time, latitude, longitude, phone, services ";

	private static final String SQL_GET_NEAREST_PARTNER = 
			"SELECT " + FIELDS + 
			"FROM " + TableNames.PARTNERS +
			" ORDER BY ida_distance(latitude, longitude, ?, ?)";

	private static final String SQL_GET_NEAREST_PARTNER_UNORDERED = 
			"SELECT " + FIELDS + 
			"FROM " + TableNames.PARTNERS;

	private static final String SQL_SELECT_VIEWPORT = 
			"SELECT " + FIELDS +
			"FROM " + TableNames.PARTNERS + 
			" WHERE " +
			"	latitude >= ? AND longitude >= ? " +
			"AND latitude <= ? AND longitude <= ? ";
	
	private static final String SQL_COUNT = 
			"SELECT COUNT(*) FROM "+TableNames.PARTNERS;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private Partner extract(ResultSet rs) throws SQLException {
		return new Partner(
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
	public Partner getNearestPartner(GeoPoint location) {
		return jdbcTemplate.query(SQL_GET_NEAREST_PARTNER, location.asArray(), new ResultSetExtractor<Partner>() {

			@Override
			public Partner extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return extract(rs);
				}
				return null;
			}
			
		});
	}

	@Transactional(readOnly = true)
	@Override
	public List<Partner> getPartners(GeoPoint location, int page, int pageSize) {
		final int startIndex = (page-1)*pageSize;
		final int endIndex = page*pageSize;

		return jdbcTemplate.query(SQL_GET_NEAREST_PARTNER, location.asArray(), new ResultSetExtractor<List<Partner>>() {

			@Override
			public List<Partner> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List<Partner> partners = new ArrayList<Partner>();
				
				int i = 0;
				while (i < startIndex && rs.next()) {
					i++;
				}
				while (i < endIndex && rs.next()) {					
					partners.add(extract(rs));
					i++;
				}
				
				return partners;
			}
			
		});
	}

	@Transactional(readOnly = true)
	@Override
	public List<Partner> getPartners(int page, int pageSize) {
		final int startIndex = (page-1)*pageSize;
		final int endIndex = page*pageSize;

		return jdbcTemplate.query(SQL_GET_NEAREST_PARTNER_UNORDERED, new ResultSetExtractor<List<Partner>>() {

			@Override
			public List<Partner> extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List<Partner> partners = new ArrayList<Partner>();
				
				int i = 0;
				while (i < startIndex && rs.next()) {
					i++;
				}
				while (i < endIndex && rs.next()) {					
					partners.add(extract(rs));
					i++;
				}
				
				return partners;
			}
			
		});
	}

	@Transactional(readOnly = true)
	@Override
	public List<Partner> getViewportPartners(GeoPoint topLeft, GeoPoint bottomRight) {		
		return jdbcTemplate.query(SQL_SELECT_VIEWPORT, ArrayUtils.addAll(topLeft.asArray(), bottomRight.asArray()), new RowMapper<Partner>() {

			@Override
			public Partner mapRow(ResultSet rs, int row) throws SQLException {
				return extract(rs);
			}
			
		});
	}

	@Transactional(readOnly = true)
	@Override
	public int count() {
		return jdbcTemplate.queryForInt(SQL_COUNT);
	}

}
