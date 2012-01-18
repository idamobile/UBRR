package com.idamobile.server.model.locations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public abstract class AbstractLocation {

	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(AbstractLocation.class);
	
	protected int id;
	protected String name;
	protected String city;
	protected String address;
	protected String subwayStation;
	protected double latitude;
	protected double longitude;
	protected String operationTime;
	protected String services;
	
	protected AbstractLocation(int id, String name, String city, String address, String subwayStation, 
			double latitude, double longitude, String operationTime, String services) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.address = address;
		this.subwayStation = subwayStation;
		this.latitude = latitude;
		this.longitude = longitude;
		this.operationTime = operationTime;
		this.services = services;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name == null? StringUtils.EMPTY : name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city == null? StringUtils.EMPTY : city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getSubwayStation() {
		return subwayStation == null? StringUtils.EMPTY : subwayStation;
	}
	public void setSubwayStation(String subwayStation) {
		this.subwayStation = subwayStation;
	}
	
	public String getAddress() {
		return address == null? StringUtils.EMPTY : address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getOperationTime() {
		return operationTime == null? StringUtils.EMPTY : operationTime;
	}
	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}
	
	public String getServices() {
		return services == null? StringUtils.EMPTY : services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	
	protected List<String> getServicesList() {
		String[] items = getServices().split("/");
//		List<LocationService> result = new ArrayList<LocationService>();
		List<String> result = new ArrayList<String>();
		for (String item: items) {
			item = item.trim();
			if (item.length() == 0) {
				continue;
			}
			result.add(item);			
			
//			LocationService service = LocationService.valueOf(Integer.valueOf(item)); 
//			if (service != null) {
//				result.add(service);			
//			}else {
//				log.warn("Unknkown service id: "+item);
//			}
		}
		return result;
	}

	public GeoPoint getLocation() {
		return new GeoPoint(latitude, longitude);
	}
}
