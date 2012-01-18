package com.idamobile.server.dao.core.locations;

import java.util.List;

import com.idamobile.server.model.locations.CreditPoint;
import com.idamobile.server.model.locations.GeoPoint;

public interface CreditPointDao {
	int count();
	
	List<CreditPoint> get(int page, int pageSize);

	CreditPoint get(GeoPoint location);
}
