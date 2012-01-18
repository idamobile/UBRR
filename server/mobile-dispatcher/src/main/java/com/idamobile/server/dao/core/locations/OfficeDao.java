package com.idamobile.server.dao.core.locations;

import java.util.List;

import com.idamobile.server.model.locations.GeoPoint;
import com.idamobile.server.model.locations.Office;

public interface OfficeDao {

	int count();
	
	List<Office> get(int page, int pageSize);	
	
	Office get(GeoPoint location);
}
