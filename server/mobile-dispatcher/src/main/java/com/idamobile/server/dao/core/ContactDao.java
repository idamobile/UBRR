package com.idamobile.server.dao.core;

import java.util.List;

import com.idamobile.server.model.Contact;

public interface ContactDao {
	List<Contact> all();
	
	Contact findByKey(String key);
}
