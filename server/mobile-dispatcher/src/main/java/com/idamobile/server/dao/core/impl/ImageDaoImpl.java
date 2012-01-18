package com.idamobile.server.dao.core.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.idamobile.protocol.ubrr.Commons.Platform;
import com.idamobile.protocol.ubrr.Commons.Resolution;
import com.idamobile.server.dao.core.ImageDao;
import com.idamobile.server.util.ImageLoader;

@Repository
public class ImageDaoImpl implements ImageDao{

	private static final String SQL_SELECT_IMAGE_TEMPLATE = "SELECT image_id, image FROM ida_images WHERE image_id = '%s' AND platform = '%s' AND resolution = '%s' ";
	private static final String SQL_UNION_KEYWORD = "UNION ALL ";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ImageLoader imageLoader;

	@Override
	public Map<String, byte[]> get(Platform platform, Resolution resolution, String imageIds[]) {
		StringBuilder query = new StringBuilder();
		for (int i = 0; i<imageIds.length; i++) {
			query.append(String.format(SQL_SELECT_IMAGE_TEMPLATE, imageIds[i], BannerDaoImpl.convertPlatform(platform), BannerDaoImpl.convertResolution(resolution)));
			if (i < imageIds.length-1) {
				query.append(SQL_UNION_KEYWORD);
			}
		}
		
		Map<String, byte[]> map = new HashMap<String, byte[]>();
		
		if (query.length() == 0) {
			return map;
		}
		
		map = jdbcTemplate.query(query.toString(), new ResultSetExtractor<Map<String, byte[]>>() {

			@Override
			public Map<String, byte[]> extractData(ResultSet rs) throws SQLException,
					DataAccessException {				
				Map<String, byte[]> result = new HashMap<String, byte[]>();
				while (rs.next()) {
					result.put(rs.getString(1), imageLoader.getImageBytes(rs.getString(2)));
				}
				return result;
			}
		});
		
		return map;
	}
	

}
