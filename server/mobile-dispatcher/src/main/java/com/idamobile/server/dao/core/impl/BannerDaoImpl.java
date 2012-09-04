package com.idamobile.server.dao.core.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.protocol.ubrr.Commons.Platform;
import com.idamobile.protocol.ubrr.Commons.Resolution;
import com.idamobile.server.dao.core.BannerDao;
import com.idamobile.server.model.Banner;

@Repository
public class BannerDaoImpl implements BannerDao{

	private static final String SQL_SELECT_BANNERS =
			"SELECT " +
					"	order_number, title, url, image_id, text " +
					"FROM " +
					"	ida_banners b ";

	private static final String SQL_COUNT_BANNERS =
			"SELECT count(*) FROM ida_banners";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	@Override
	public List<Banner> get() {

		return jdbcTemplate.query(SQL_SELECT_BANNERS, new RowMapper<Banner>() {

			@Override
			public Banner mapRow(ResultSet rs, int row) throws SQLException {
				Banner b = new Banner(rs.getString(2), rs.getString(3), rs.getString(5), rs.getInt(1), rs.getString(4));
				return b;
			}

		});
	}

	public static String convertPlatform(Platform platform) {
		switch (platform) {
		case iOS:
			return "iOS";
		case iPad:
			return "iPad";
		default:
			return "Android";
		}
	}

	public static String convertResolution(Resolution resolution) {
		switch (resolution) {
		case HDPI:
			return "hdpi";
		default:
			return "mdpi";
		}
	}

	@Transactional(readOnly = true)
	@Override
	public int count() {
		return jdbcTemplate.queryForInt(SQL_COUNT_BANNERS);
	}


}
