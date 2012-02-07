package com.idamobile.server.dao.core;

import java.util.List;

import com.idamobile.server.model.CurrencyRate;

public interface CurrencyRateDao {

	List<CurrencyRate> all();
	
	long getLastUpdateTime();
}
