package com.idamobile.server.dao.core.locations.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.server.dao.core.impl.TableNames;
import com.idamobile.server.dao.core.locations.PartnerDao;
import com.idamobile.server.model.locations.GeoPoint;
import com.idamobile.server.model.locations.Partner;

@Repository
public class PartnerDaoImpl implements PartnerDao {

	private static final String PRODUCTS_PARAM = "products";

	private static final String FILTER_SUB_QUERY = "SELECT partner_id FROM ida_partner_card WHERE card in (:" + PRODUCTS_PARAM + ")";

	private static final String FIELDS = "partner_id, zip_code, city, subway_station, name, address, operation_time, latitude, longitude, phone, services, short_services ";

	private static final String SQL_GET_NEAREST_PARTNER = 
			"SELECT " + FIELDS + 
			"FROM " + TableNames.PARTNERS +
			" WHERE partner_id in ( " + FILTER_SUB_QUERY + ")" +
			" ORDER BY ida_distance(latitude, longitude, :myLat, :myLng)";

	private static final String SQL_PARTNERS_UNORDERED = 
			"SELECT " + FIELDS + 
			"FROM " + TableNames.PARTNERS +
			" WHERE partner_id in ( " + FILTER_SUB_QUERY + ")";

	private static final String SQL_SELECT_VIEWPORT = 
			"SELECT " + FIELDS +
			"FROM " + TableNames.PARTNERS + 
			" WHERE " +
			"	latitude >= :topLeftLat AND longitude >= :topLeftLng " +
			"AND latitude <= :btmRightLat AND longitude <= :btmRightLng " +
			"AND partner_id in ( " + FILTER_SUB_QUERY + ")";
	
	private static final String SQL_COUNT = 
			"SELECT COUNT(*) FROM "+ TableNames.PARTNERS +
			" WHERE partner_id in ( " + FILTER_SUB_QUERY + ")";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	private NamedParameterJdbcTemplate getJdbcTemplate() {
		if (namedJdbcTemplate == null)
			namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		
		return namedJdbcTemplate;
	}
	
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
				rs.getString(11),// services
				rs.getString(12) // short_services
			);
	}
	
	@SuppressWarnings("serial")
	@Transactional(readOnly = true)
	@Override
	public Partner getNearestPartner(final GeoPoint location, final List<String> products) {
		return getJdbcTemplate().query(
				SQL_GET_NEAREST_PARTNER,
	
				new HashMap<String, Object>() {{
					put("myLat", location.latitude);
					put("myLng", location.longitude);
					put(PRODUCTS_PARAM, products);
				}},
				
				new ResultSetExtractor<Partner>() {
					@Override
					public Partner extractData(ResultSet rs) throws SQLException, DataAccessException {
						if (rs.next()) {
							return extract(rs);
						} else {
							return null;
						}
					}
				}
		);
	}

	@SuppressWarnings("serial")
	@Transactional(readOnly = true)
	@Override
	public List<Partner> getPartners(final GeoPoint location, int page, int pageSize, final List<String> products) {
		final int startIndex = (page-1)*pageSize;
		final int endIndex = page*pageSize;

		return getJdbcTemplate().query(
				SQL_GET_NEAREST_PARTNER,
				
				new HashMap<String, Object>() {{
					put("myLat", location.latitude);
					put("myLng", location.longitude);
					put(PRODUCTS_PARAM, products);
				}},
				
				new ResultSetExtractor<List<Partner>>() {
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
				}
			);
		/*
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
			
		});*/
	}

	@Transactional(readOnly = true)
	@Override
	public List<Partner> getPartners(int page, int pageSize, List<String> products) {
		final int startIndex = (page-1)*pageSize;
		final int endIndex = page*pageSize;

		return getJdbcTemplate().query(
				SQL_PARTNERS_UNORDERED, 
				
				Collections.singletonMap(PRODUCTS_PARAM, products), 
				
				new ResultSetExtractor<List<Partner>>() {
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
				}
			);
/*		return simpleJdbcTemplate.query(
				SQL_PARTNERS_UNORDERED,
				new RowMapper<Partner>() {
					@Override
					public Partner mapRow(ResultSet rs, int row) throws SQLException {
						return null;
					}
				},
				Collections.singletonMap(PRODUCTS_PARAM, products)
			);*/
		
/*		return jdbcTemplate.query(SQL_PARTNERS_UNORDERED, new ResultSetExtractor<List<Partner>>() {

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
			
		});*/
	}

	@SuppressWarnings("serial")
	@Transactional(readOnly = true)
	@Override
	public List<Partner> getViewportPartners(final GeoPoint topLeft, final GeoPoint bottomRight, final List<String> products) {
			return getJdbcTemplate().query(
					SQL_SELECT_VIEWPORT,
					
					new HashMap<String, Object>() {{
						put("topLeftLat", topLeft.latitude);
						put("topLeftLng", topLeft.longitude);
						put("btmRightLat", bottomRight.latitude);
						put("btmRightLng", bottomRight.longitude);
						put(PRODUCTS_PARAM, products);
					}},
					
					new RowMapper<Partner>() {
						@Override
						public Partner mapRow(ResultSet rs, int row) throws SQLException {
							return extract(rs);
						}
					} 
			);
	}

	@Transactional(readOnly = true)
	@Override
	public int count(List<String> products) {
		return getJdbcTemplate().queryForInt(SQL_COUNT, Collections.singletonMap(PRODUCTS_PARAM, products));
	}
}
