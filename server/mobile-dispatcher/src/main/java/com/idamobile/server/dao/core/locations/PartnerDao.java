package com.idamobile.server.dao.core.locations;

import java.util.List;

import com.idamobile.server.model.locations.GeoPoint;
import com.idamobile.server.model.locations.Partner;

public interface PartnerDao {

	int count();
	
	List<Partner> get(int page, int pageSize);	
	
	Partner get(GeoPoint location);
}
