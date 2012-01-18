package com.idamobile.server.dao.core.impl;

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

import com.idamobile.server.dao.core.ContactDao;
import com.idamobile.server.model.Contact;

@Repository
public class ContactDaoImpl implements ContactDao {

	private static final String SQL_SELECT_CONTACT = "select contact_id, contact_type, value, large_value from ida_contacts where contact_id = ?";
	private static final String SQL_SELECT_ALL_CONTACTS = "select contact_id, contact_type, value, large_value from ida_contacts";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private Contact extractContact(final ResultSet rs) throws SQLException {
		String key = rs.getString(1);
		String type = rs.getString(2).toLowerCase();							
		return new Contact(key, type, Contact.TYPE_LOB.equals(type)?rs.getString(4):rs.getString(3));		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Contact> all() {		
		return jdbcTemplate.query(SQL_SELECT_ALL_CONTACTS, new RowMapper<Contact>() {

			@Override
			public Contact mapRow(ResultSet rs, int row) throws SQLException {
				return extractContact(rs);
			}
			
		});
	}

	@Transactional(readOnly = true)
	@Override
	public Contact findByKey(final String key) {
		return jdbcTemplate.query(SQL_SELECT_CONTACT, new Object[] { key },
				new ResultSetExtractor<Contact>() {

					@Override
					public Contact extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						return rs.next()? extractContact(rs):new Contact();
					}
				});
	}

}
