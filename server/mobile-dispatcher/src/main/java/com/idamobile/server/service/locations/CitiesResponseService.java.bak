package com.idamobile.server.service.locations;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Partners.CitiesRequest;
import com.idamobile.protocol.ubrr.Partners.CitiesResponse;
import com.idamobile.protocol.ubrr.Partners.CityMessage;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.dao.core.locations.CityDao;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class CitiesResponseService extends AbstractMessageService<CitiesRequest> {

	@Autowired
	private CityDao cityDao;
	
	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasCitiesRequest();
	}

	@Override
	protected CitiesRequest extractRequestMessage(MBSRequest request) {
		return request.getCitiesRequest();
	}

	@Override
	protected void processMessage(CitiesRequest request, Builder responseBuilder) {		
		CitiesResponse.Builder builder = CitiesResponse.newBuilder();
		
		for (Map.Entry<String, List<String>> entry: cityDao.all().entrySet()) {
			builder.addCities(CityMessage.newBuilder()
					.setCity(entry.getKey())
					.addAllSubwayStations(entry.getValue()));
		}
		
		responseBuilder.setCitiesResponse(builder.build());
		
	}
}
