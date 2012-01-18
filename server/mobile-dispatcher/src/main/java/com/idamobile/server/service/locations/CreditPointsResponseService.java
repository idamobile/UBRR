package com.idamobile.server.service.locations;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Locations.CreditPointsRequest;
import com.idamobile.protocol.ubrr.Locations.CreditPointsResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.ServerConstants;
import com.idamobile.server.dao.core.UpdatesDao;
import com.idamobile.server.dao.core.locations.CreditPointDao;
import com.idamobile.server.model.locations.CreditPoint;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class CreditPointsResponseService extends AbstractMessageService<CreditPointsRequest> {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(CreditPointsResponseService.class);
	
	@Value("${idaserver.locations.pageSize}")
	private int pageSize;
	
	@Autowired
	private CreditPointDao creditPointDao;
	
	@Autowired
	private UpdatesDao updatesDao;

	@Override
	public void processMessage(CreditPointsRequest request, Builder responseBuilder) {
		
		if (pageSize <= 0) {
			pageSize = ServerConstants.DEFAULT_PAGE_SIZE;
		}
		
		CreditPointsResponse.Builder builder = CreditPointsResponse.newBuilder();
		
		long storedUpdateTime = updatesDao.getUpdateTime(UpdatesDao.ENTITY_LOC_CREDIT_POINTS);
		
		if (request.getLastUpdateTime() < storedUpdateTime) {
			int page = request.getPage() <= 0? 1: request.getPage();					
			int creditPointsCount = creditPointDao.count();

			builder.setPage(page);
			builder.setTotalPages((int)Math.ceil(1.0*creditPointsCount/pageSize));
			
			for (CreditPoint creditPoint: creditPointDao.get(page, pageSize)) {
				builder.addCreditPoints(creditPoint.createMessage());
			}
		
		}
		builder.setLastUpdateTime(storedUpdateTime);
		
		responseBuilder.setCreditPointsResponse(builder.build());
	}

	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasCreditPointsRequest();
	}

	@Override
	protected CreditPointsRequest extractRequestMessage(MBSRequest request) {
		return request.getCreditPointsRequest();
	}
	
}
