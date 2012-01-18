package com.idamobile.server.dao.core.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idamobile.server.dao.core.NewsDao;
import com.idamobile.server.model.News;

@Repository
public class NewsDaoImpl implements NewsDao{

	private static final String SQL_SELECT_NEWS = 
//		"select news_id, title, preview, html_body, url, category, creation_date from ida_news";
		"select news_id, title, preview, html_body, url, creation_date from " + TableNames.NEWS;
		
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly = true)
	@Override
	public List<News> all() {
		return jdbcTemplate.query(SQL_SELECT_NEWS, new RowMapper<News>() {

			@Override
			public News mapRow(ResultSet rs, int row) throws SQLException {
//				return new News(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getLong(7));
				return new News(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(5), rs.getString(4), rs.getLong(6));
			}
			
		});
	}

}
