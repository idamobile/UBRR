package com.idamobile.server.dao.core;

/**
 * Used to access properties stored in IDA_PROPERTIES table
 * @author zjor
 *
 */
public interface PropertyDao {

	/**
	 * Returns property value or an empty string if not found
	 * @param name
	 * @return
	 */
	String getProperty(String name);
}
