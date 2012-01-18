package com.idamobile.server.model.locations;

import java.math.BigDecimal;

import com.idamobile.protocol.ubrr.Commons.GeoPointMessage;

/**
 * Convenient class for passing parameters into SQL queries. BigDecimal is
 * converted into DECIMAL data type
 * 
 * @author zjor
 * 
 */
public class GeoPoint {

	public BigDecimal latitude;

	public BigDecimal longitude;

	public GeoPoint() {
		latitude = new BigDecimal(0.0);
		longitude = new BigDecimal(0.0);
	}

	public GeoPoint(double latitude, double longitude) {
		this.latitude = new BigDecimal(latitude);
		this.longitude = new BigDecimal(longitude);
	}

	public GeoPoint(BigDecimal latitude, BigDecimal longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public GeoPoint(GeoPointMessage msg) {
		latitude = new BigDecimal(msg.getLatitude());
		longitude = new BigDecimal(msg.getLongitude());
	}

	public GeoPointMessage asMessage() {
		GeoPointMessage.Builder builder = GeoPointMessage.newBuilder();

		if (latitude != null)
			builder.setLatitude(latitude.doubleValue());

		if (longitude != null)
			builder.setLongitude(longitude.doubleValue());

		return builder.build();
	}

	public Object[] asArray() {
		return new Object[] { latitude, longitude };
	}

	@Override
	public String toString() {
		return String.format("(%f, %f)", latitude.doubleValue(), longitude.doubleValue());
	}

	/**
	 * Calculates distance between two points in kilometers
	 * @see http://www.zipcodeworld.com/samples/distance.java.html
	 * @param a
	 * @param b
	 * @return
	 */
	public static double distance(GeoPoint a, GeoPoint b) {
		
		double lat1 = a.latitude.doubleValue();
		double lat2 = b.latitude.doubleValue();
		double lon1 = a.longitude.doubleValue();
		double lon2 = b.longitude.doubleValue();
		
		double theta = lon1-lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return dist;
	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

}
