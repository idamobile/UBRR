package com.idamobile.server.dao.integration;

import java.util.List;

import com.idamobile.server.model.integration.TosJobConfiguration;

public interface TosIntegrationDao {
	List<TosJobConfiguration> get();
}
