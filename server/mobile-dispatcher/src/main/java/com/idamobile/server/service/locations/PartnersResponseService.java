package com.idamobile.server.service.locations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Partners.PartnersRequest;
import com.idamobile.protocol.ubrr.Partners.PartnersResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.ServerConstants;
import com.idamobile.server.dao.core.locations.PartnerDao;
import com.idamobile.server.model.locations.GeoPoint;
import com.idamobile.server.model.locations.Partner;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class PartnersResponseService extends AbstractMessageService<PartnersRequest> {

	@Value("${idaserver.locations.pageSize}")
	private int pageSize;
	
	@Autowired
	private PartnerDao partnerDao;
	
	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasPartnersRequest();
	}

	@Override
	protected PartnersRequest extractRequestMessage(MBSRequest request) {
		return request.getPartnersRequest();
	}

	@Override
	protected void processMessage(PartnersRequest request, Builder responseBuilder) {
		if (pageSize <= 0) {
			pageSize = ServerConstants.DEFAULT_PAGE_SIZE;
		}
		
		int page = request.getPage();
		if (page <= 0) {
			page = 1;
		}

		List<String> products = request.getProductsList();
		List<Partner> partners = null;
		
		if (request.hasLocation()) {
			partners = partnerDao.getPartners(new GeoPoint(request.getLocation()), page, pageSize, products);
		} else {
			partners = partnerDao.getPartners(page, pageSize, products);
		}

		PartnersResponse.Builder builder = PartnersResponse.newBuilder();
		
		for (Partner p: partners) {
			builder.addPartners(p.createMessage());
		}

		builder.setPage(page);
		int count = partnerDao.count(products);
		builder.setTotalPages((int) Math.ceil(1.0 * count/pageSize));
		
		responseBuilder.setPartnersResponse(builder);
	}

}
