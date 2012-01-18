package com.idamobile.server.model.locations;

import com.idamobile.protocol.ubrr.Locations.AtmMessage;

public class ATM extends AbstractLocation {

	public ATM(int id, String name, String city, String address, String subwayStation, 
			double latitude, double longitude, String operationTime, String services) {
		super(id, name, city, address, subwayStation, latitude, longitude, operationTime, services);
	}
	
	public AtmMessage createMessage() {
		AtmMessage.Builder builder = AtmMessage.newBuilder();
		
		builder.setAtmId(id);
		builder.setName(getName());
		builder.setCity(getCity());		
		builder.setAddress(getAddress());	
		builder.setSubwayStation(getSubwayStation());
		builder.setOperationTime(getOperationTime());
		
		builder.setLatitude(latitude);
		builder.setLongitude(longitude);
		
		builder.addAllServices(getServicesList());
		
		return builder.build();
	}
}
