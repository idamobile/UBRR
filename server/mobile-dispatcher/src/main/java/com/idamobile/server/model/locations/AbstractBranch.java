package com.idamobile.server.model.locations;

public abstract class AbstractBranch extends AbstractLocation {
	
	protected String zipCode;
	protected String phone;
	
	protected AbstractBranch(int id, String name, String city, String address, String zipCode, 
			String subwayStation, double latitude, double longitude, String phone, 
			String operationTime, String services) {
		super(id, name, city, address, subwayStation, latitude, longitude, operationTime, services);
		this.zipCode = zipCode;
		this.phone = phone;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
