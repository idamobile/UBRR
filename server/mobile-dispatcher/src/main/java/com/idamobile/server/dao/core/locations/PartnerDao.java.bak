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
	 * @param products products to filter partners by
	 * @return
	 */
	Partner getNearestPartner(GeoPoint location, List<String> products);
	
	/**
	 * Returns page of partners sorted by increasing distance to the given location
	 * @param location
	 * @param products products to filter partners by
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<Partner> getPartners(GeoPoint location, List<String> products, int page, int pageSize);
	
	/**
	 * Returns unordered list of partners
	 * @param products products to filter partners by
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<Partner> getPartners(List<String> products, int page, int pageSize);
	
	/**
	 * Returns page of partners by given city and subway station
	 * @param city
	 * @param station
	 * @param products products to filter partners by
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<Partner> getPartners(String city, String station, List<String> products, int page, int pageSize);
	
	/**
	 * Returns partners from specified rectangular area
	 * @param topLeft
	 * @param bottomRight
	 * @param products products to filter partners by
	 * @return
	 */
	List<Partner> getViewportPartners(GeoPoint topLeft, GeoPoint bottomRight, List<String> products);
	
	/**
	 * Returns number of partners
	 * @return
	 */
	int count(List<String> products);
	
	/**
	 * Returns number of partners by subway station
	 * @return
	 */
	int countBySubway(String city, String station, List<String> products);
}