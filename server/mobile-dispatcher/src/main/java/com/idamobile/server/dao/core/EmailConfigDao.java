package com.idamobile.server.dao.core;

import java.util.List;

import com.idamobile.server.model.EmailConfig;

public interface EmailConfigDao {

	EmailConfig get(int emailType);
	
	List<EmailConfig> all();
}
