package com.idamobile.server.service.locations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Partners.PartnersBySubwayRequest;
import com.idamobile.protocol.ubrr.Partners.PartnersResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.ServerConstants;
import com.idamobile.server.dao.core.locations.PartnerDao;
import com.idamobile.server.model.locations.Partner;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class PartnersBySubwayResponseService extends AbstractMessageService<PartnersBySubwayRequest> {

	@Value("${idaserver.locations.pageSize}")
	private int pageSize;
	
	@Autowired
	private PartnerDao partnerDao;
	
	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasPartnersBySubwayRequest();
	}

	@Override
	protected PartnersBySubwayRequest extractRequestMessage(MBSRequest request) {
		return request.getPartnersBySubwayRequest();
	}

	@Override
	protected void processMessage(PartnersBySubwayRequest request, Builder responseBuilder) {
		if (pageSize <= 0) {
			pageSize = ServerConstants.DEFAULT_PAGE_SIZE;
		}
		
		int page = request.getPage();
		if (page <= 0) {
			page = 1;
		}

		String city = request.getCity();
		String station = request.getSubwayStation();
		List<String> products = request.getProductsList();
		
		PartnersResponse.Builder builder = PartnersResponse.newBuilder();
		
		if (products != null && !products.isEmpty()) {
			List<Partner> partners = partnerDao.getPartners(city, station, products, page, pageSize);
			for (Partner p: partners) {
				builder.addPartners(p.createMessage());
			}

			builder.setPage(page);
			int count = partnerDao.countBySubway(city, station, products);
			builder.setTotalPages((int) Math.ceil(1.0 * count/pageSize));
		}
		
		responseBuilder.setPartnersResponse(builder);
	}

}
