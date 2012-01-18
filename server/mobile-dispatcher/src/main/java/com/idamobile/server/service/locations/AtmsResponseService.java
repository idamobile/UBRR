package com.idamobile.server.service.locations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Locations.AtmsRequest;
import com.idamobile.protocol.ubrr.Locations.AtmsResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.ServerConstants;
import com.idamobile.server.dao.core.UpdatesDao;
import com.idamobile.server.dao.core.locations.AtmDao;
import com.idamobile.server.model.locations.ATM;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class AtmsResponseService extends AbstractMessageService<AtmsRequest>{

	@Value("${idaserver.locations.pageSize}")
	private int pageSize;
	
	@Autowired
	private AtmDao atmDao;
	
	@Autowired
	private UpdatesDao updatesDao;
	
	@Override
	protected void processMessage(AtmsRequest request, Builder responseBuilder) {		
		if (pageSize <= 0) {
			pageSize = ServerConstants.DEFAULT_PAGE_SIZE;
		}
		
		AtmsResponse.Builder builder = AtmsResponse.newBuilder(); 
				
		long storedUpdateTime = updatesDao.getUpdateTime(UpdatesDao.ENTITY_LOC_ATMS);
		
		if (request.getLastUpdateTime() < storedUpdateTime) {		
			int page = request.getPage() <= 0? 1: request.getPage();					
			int atmsCount = atmDao.count();
			
			builder.setPage(page);
			builder.setTotalPages((int)Math.ceil(1.0*atmsCount/pageSize));
			
			for (ATM atm: atmDao.get(page, pageSize)) {
				builder.addAtms(atm.createMessage());
			}		
		}
		builder.setLastUpdateTime(storedUpdateTime);
		
		responseBuilder.setAtmsResponse(builder.build());
		
	}

	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasAtmsRequest();
	}

	@Override
	protected AtmsRequest extractRequestMessage(MBSRequest request) {
		return request.getAtmsRequest();
	}

}
