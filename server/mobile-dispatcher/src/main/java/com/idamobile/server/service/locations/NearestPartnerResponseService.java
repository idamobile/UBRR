package com.idamobile.server.service.locations;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Partners.NearestPartnerRequest;
import com.idamobile.protocol.ubrr.Partners.NearestPartnerResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.dao.core.locations.PartnerDao;
import com.idamobile.server.model.locations.GeoPoint;
import com.idamobile.server.model.locations.Partner;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class NearestPartnerResponseService extends AbstractMessageService<NearestPartnerRequest>{
	
	private Logger log = Logger.getLogger(NearestPartnerResponseService.class);
	
	@Autowired
	private PartnerDao partnerDao;
	
	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasNearestPartnerRequest();
	}

	@Override
	protected NearestPartnerRequest extractRequestMessage(MBSRequest request) {
		return request.getNearestPartnerRequest();
	}

	@Override
	protected void processMessage(NearestPartnerRequest request, Builder responseBuilder) {
				
		NearestPartnerResponse.Builder builder = NearestPartnerResponse.newBuilder();

		GeoPoint location = new GeoPoint(request.getLocation());
		Partner partner = partnerDao.get(location);
		if (partner != null) {
			builder.setPartner(partner.createMessage());
		} else {
			log.warn("Nearest partner is null for location: " + location);
		}
		
		responseBuilder.setNearestPartnerResponse(builder.build());
		
	}

}
