package com.idamobile.server.dao.core.locations.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.server.dao.core.impl.TableNames;
import com.idamobile.server.dao.core.locations.PartnerDao;
import com.idamobile.server.model.locations.GeoPoint;
import com.idamobile.server.model.locations.Partner;

@Repository
public class PartnerDaoImpl implements PartnerDao {

	private static final String SQL_COUNT_PARTNERS = 
		"SELECT COUNT(*) FROM " + TableNames.PARTNERS;
	
	private static final String FIELDS = " p.partner_id, zip_code, city, subway_station, name, address, operation_time, latitude, longitude, phone, services, short_services ";
	
//	private static final String SQL_SELECT_PARTNERS = 
//			"SELECT " + FIELDS +
//			"FROM " + TableNames.PARTNERS +
//			"	WHERE order_num >= ? AND order_num < ? " +
//			"ORDER BY order_num ASC";
	
	private static final String SQL_SELECT_PARTNERS = 
		"SELECT " + FIELDS + ", card " +
		"FROM " + TableNames.PARTNERS + " p LEFT OUTER JOIN ida_partner_card pc on p.partner_id = pc.partner_id " +
		"	WHERE order_num >= ? AND order_num < ? " +
		"ORDER BY order_num ASC";

	private static final String SQL_SELECT_NEAREST_PARTNER = 
		"SELECT * FROM (SELECT " + FIELDS +
		"FROM " + TableNames.PARTNERS + " p " +
		" ORDER BY ida_distance(latitude, longitude, ?, ?) " +
		") src WHERE ROWNUM < 2";
	
	private static final String SQL_SELECT_CARDS = "select unique card from ida_partner_card where partner_id = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	@Override
	public int count() {
		return jdbcTemplate.queryForInt(SQL_COUNT_PARTNERS);
	}

	private Partner extract(ResultSet rs) throws SQLException {
		int id = rs.getInt(1);
		List<String> cards = jdbcTemplate.queryForList(SQL_SELECT_CARDS, String.class, id);
		return new Partner(
				id,
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
				rs.getString(12),// short_services
				cards
			);
	}
	
	private Partner extractCore(ResultSet rs) {
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
				rs.getString(12),// short_services
				new ArrayList<String>()
			);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Partner> get(final int page, final int pageSize) {
		int startIndex = (page-1) * pageSize;
		int endIndex = page * pageSize;
		
		return jdbcTemplate.query(SQL_SELECT_PARTNERS, new Object[] {startIndex, endIndex}, new ResultSetExtractor<List<Partner>>() {
			@Override
			public List<Partner> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, Partner> data = new HashMap<Integer, Partner>();
				Integer id = null;
				Partner item = null;
				
				while (rs.next()) {
					id = rs.getInt(1);
					item = data.get(id);
					
					if (item == null) {
						item = extractCore(rs);
						data.put(id, item);
					}
					item.getCards().add(rs.getString(13));
				}
				
				return new ArrayList<Partner>(data.values());
			}
		});
	}
	
/*
	@Transactional(readOnly = true)
	@Override
	public List<Partner> get(final int page, final int pageSize) {

		int startIndex = (page-1) * pageSize;
		int endIndex = page*pageSize;

		return jdbcTemplate.query(SQL_SELECT_PARTNERS, new Object[] {startIndex, endIndex}, new RowMapper<Partner>() {
			
			@Override
			public Partner mapRow(ResultSet rs, int row) throws SQLException {
				return extract(rs);
			}
		});
	}
*/
	@Transactional(readOnly = true)
	@Override
	public Partner get(GeoPoint location) {
		return jdbcTemplate.query(SQL_SELECT_NEAREST_PARTNER, location.asArray(), new ResultSetExtractor<Partner>() {

			@Override
			public Partner extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return extract(rs);
				}else {
					return null;
				}
			}
			
		});
	}

}
