package com.idamobile.server.dao.core.locations;

import java.util.List;

import com.idamobile.server.model.locations.ATM;
import com.idamobile.server.model.locations.GeoPoint;

public interface AtmDao {
	int count();
	
	List<ATM> get(int page, int pageSize);

	ATM get(GeoPoint location);
	
}
