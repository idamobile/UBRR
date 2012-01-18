package com.idamobile.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Banners.BannersRequest;
import com.idamobile.protocol.ubrr.Banners.BannersResponse;
import com.idamobile.protocol.ubrr.Commons.Platform;
import com.idamobile.protocol.ubrr.Commons.Resolution;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.ServerConstants;
import com.idamobile.server.dao.core.BannerDao;
import com.idamobile.server.dao.core.UpdatesDao;
import com.idamobile.server.model.Banner;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class BannersResponseService extends AbstractMessageService<BannersRequest>{

	@Autowired
	private BannerDao bannerDao;
	
	@Autowired
	private UpdatesDao updatesDao;
	
	@Override
	protected void processMessage(BannersRequest request, Builder responseBuilder) {
		
		BannersResponse.Builder builder = BannersResponse.newBuilder();
		
		long storedLastUpdate = updatesDao.getUpdateTime(UpdatesDao.ENTITY_BANNERS);

		if (request.getLastUpdateTime() < storedLastUpdate) {			

			for (Banner banner: bannerDao.get()) {
				builder.addBanners(banner.createMessage());
			}			
		}
		
		builder.setLastUpdateTime(storedLastUpdate);
		responseBuilder.setBannersResponse(builder.build());
		
	}

	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasBannersRequest();
	}

	@Override
	protected BannersRequest extractRequestMessage(MBSRequest request) {
		return request.getBannersRequest();
	}

}
