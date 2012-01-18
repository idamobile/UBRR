package com.idamobile.server.service.locations;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Locations.NearestLocationRequest;
import com.idamobile.protocol.ubrr.Locations.NearestLocationResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.dao.core.locations.AtmDao;
import com.idamobile.server.dao.core.locations.CreditPointDao;
import com.idamobile.server.dao.core.locations.OfficeDao;
import com.idamobile.server.model.locations.ATM;
import com.idamobile.server.model.locations.CreditPoint;
import com.idamobile.server.model.locations.GeoPoint;
import com.idamobile.server.model.locations.Office;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class NearestLocationResponseService extends AbstractMessageService<NearestLocationRequest>{

	private Logger log = Logger.getLogger(NearestLocationResponseService.class);
	
	@Autowired
	private AtmDao atmDao;
	
	@Autowired
	private OfficeDao officeDao;

	@Autowired
	private CreditPointDao creditPointDao;
	
	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasNearestLocationRequest();
	}

	@Override
	protected NearestLocationRequest extractRequestMessage(MBSRequest request) {
		return request.getNearestLocationRequest();
	}

	@Override
	protected void processMessage(NearestLocationRequest request, Builder responseBuilder) {
		
		NearestLocationResponse.Builder builder = NearestLocationResponse.newBuilder();		
		
		GeoPoint location = new GeoPoint(request.getLocation());
		ATM atm = atmDao.get(location);
		Office office = officeDao.get(location);
		CreditPoint creditPoint = creditPointDao.get(location);
		
		if (atm == null || office == null || creditPoint == null) {
			log.warn("Nearest location is null for location: " + location);
			responseBuilder.setNearestLocationResponse(builder.build());
			return;
		}		
		
		switch (request.getType()) {
		case ATM:
			builder.setNearestAtm(atm.createMessage());
			break;
		case OFFICE:
			builder.setNearestOffice(office.createMessage());
			break;
		case CREDIT_POINT:
			builder.setNearestCreditPoint(creditPoint.createMessage());
			break;
		case NEAREST:
			double atmDist = GeoPoint.distance(atm.getLocation(), location);
			double officeDist = GeoPoint.distance(office.getLocation(), location);
			double creditPointDist = GeoPoint.distance(creditPoint.getLocation(), location);
			
			if (atmDist < officeDist && atmDist < creditPointDist) {
				builder.setNearestAtm(atm.createMessage());
			} else if (officeDist < creditPointDist && officeDist < atmDist) {
				builder.setNearestOffice(office.createMessage());
			} else {
				builder.setNearestCreditPoint(creditPoint.createMessage());
			}
			break;
		case ALL:
			builder.setNearestAtm(atm.createMessage());
			builder.setNearestOffice(office.createMessage());
			builder.setNearestCreditPoint(creditPoint.createMessage());
			break;
		}
		
		responseBuilder.setNearestLocationResponse(builder.build());
	}

}
