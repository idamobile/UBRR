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

import com.idamobile.server.dao.core.locations.CityDao;

@Repository
public class CityDaoImpl implements CityDao {

	private static final String SQL_SELECT_CITIES = 
		"select unique city, subway_station from ida_partners order by city, subway_station";
		
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = true)
	@Override
	public Map<String, List<String>> all() {
		return jdbcTemplate.query(SQL_SELECT_CITIES, new ResultSetExtractor<Map<String, List<String>>>() {
			@Override
			public Map<String, List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				Map<String, List<String>> cities = new HashMap<String, List<String>>();
				List<String> stations = null;
				while (rs.next()) {
					String city = rs.getString(1);
					
					if (cities.containsKey(city)) {
						stations = cities.get(city);
					} else {
						stations = new ArrayList<String>();
						cities.put(city, stations);
					}
					stations.add(rs.getString(2));
				}
				
				return cities;
			}
		});
	}

}
