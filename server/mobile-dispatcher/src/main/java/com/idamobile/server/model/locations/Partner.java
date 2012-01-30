package com.idamobile.server.model.locations;

import com.idamobile.protocol.ubrr.Partners.PartnerMessage;

public class Partner extends AbstractBranch {

	protected String discounts;
	
	public Partner(int id, String name, String city, String address, String zipCode,
			String subwayStation, double latitude, double longitude, String phone,
			String operationTime, String services, String discounts) {
		super(id, name, city, address, zipCode, subwayStation, latitude, longitude, phone, operationTime,
				services);
		
		this.discounts = discounts;
	}
	
	public PartnerMessage createMessage() {
		PartnerMessage.Builder builder = PartnerMessage.newBuilder();
		
		builder.setPartnerId(id);
		
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
		
		if (discounts != null) 
			builder.setShortServices(discounts);
		
		return builder.build();
	}
}
