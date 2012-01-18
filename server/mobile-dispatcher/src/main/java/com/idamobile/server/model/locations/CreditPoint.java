package com.idamobile.server.model.locations;

import com.idamobile.protocol.ubrr.Locations.CreditPointMessage;

public class CreditPoint extends AbstractBranch {
	
	public CreditPoint(int id, String name, String city, String address, String zipCode,
			String subwayStation, double latitude, double longitude, String phone, 
			String operationTime, String services) {
		super(id, name, city, address, zipCode, subwayStation, latitude, longitude, 
				phone, operationTime, services);
	}
	
	public CreditPointMessage createMessage() {
		CreditPointMessage.Builder builder = CreditPointMessage.newBuilder();
		
		builder.setCreditPointId(id);
		
		builder.setName(getName());		
		builder.setCity(getCity());		
		builder.setAddress(getAddress());
		builder.setSubwayStation(getSubwayStation());
		builder.setOperationTime(getOperationTime());
		
		if (zipCode != null) {
			builder.setZipCode(zipCode);
		}
		if (phone != null) {
			builder.setPhone(phone);			
		}
		
		builder.setLatitude(latitude);
		builder.setLongitude(longitude);
		
		builder.addAllServices(getServicesList());
		
		return builder.build();
	}
}
