package com.idamobile.server.service.locations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Locations.OfficesRequest;
import com.idamobile.protocol.ubrr.Locations.OfficesResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.ServerConstants;
import com.idamobile.server.dao.core.UpdatesDao;
import com.idamobile.server.dao.core.locations.OfficeDao;
import com.idamobile.server.model.locations.Office;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class OfficesResponseService extends AbstractMessageService<OfficesRequest>{
	
	@Value("${idaserver.locations.pageSize}")
	private int pageSize;
	
	@Autowired
	private OfficeDao officeDao;
	
	@Autowired
	private UpdatesDao updatesDao;
	
	@Override
	public void processMessage(OfficesRequest request, Builder responseBuilder) {
		
		if (pageSize <= 0) {
			pageSize = ServerConstants.DEFAULT_PAGE_SIZE;
		}
		
		OfficesResponse.Builder builder = OfficesResponse.newBuilder();
		
		long storedUpdateTime = updatesDao.getUpdateTime(UpdatesDao.ENTITY_LOC_OFFICES);
		
		if (request.getLastUpdateTime() < storedUpdateTime) {
			int page = request.getPage() <= 0? 1: request.getPage();					
			int officesCount = officeDao.count();

			builder.setPage(page);
			builder.setTotalPages((int)Math.ceil(1.0*officesCount/pageSize));
			
			for (Office office: officeDao.get(page, pageSize)) {
				builder.addOffices(office.createMessage());
			}
		
		}		
		builder.setLastUpdateTime(storedUpdateTime);
		
		responseBuilder.setOfficesResponse(builder.build());
	}

	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasOfficesRequest();
	}

	@Override
	protected OfficesRequest extractRequestMessage(MBSRequest request) {
		return request.getOfficesRequest();
	}

}
