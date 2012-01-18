package com.idamobile.server.dao.core.locations;

import com.idamobile.server.model.locations.GeoPoint;
import com.idamobile.server.model.locations.Partner;

import java.util.List;

/**
 * Dao for dealing with Partner model class
 * @author zjor
 *
 */
public interface PartnerDao {
	
	/**
	 * Returns one partner which is nearest to the given location
	 * @param location
	 * @return
	 */
	Partner getNearestPartner(GeoPoint location);
	
	/**
	 * Returns page of partners sorted by increasing distance to the given location
	 * @param location
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<Partner> getPartners(GeoPoint location, int page, int pageSize);
	
	/**
	 * Returns unordered list of partners
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<Partner> getPartners(int page, int pageSize);
	
	/**
	 * Returns partners from specified rectangular area
	 * @param topLeft
	 * @param bottomRight
	 * @return
	 */
	List<Partner> getViewportPartners(GeoPoint topLeft, GeoPoint bottomRight);
	
	/**
	 * Returns number of partners
	 * @return
	 */
	int count();
}