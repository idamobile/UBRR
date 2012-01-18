package com.idamobile.server.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.News.NewsRequest;
import com.idamobile.protocol.ubrr.News.NewsResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.dao.core.NewsDao;
import com.idamobile.server.model.News;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class NewsResponseService extends AbstractMessageService<NewsRequest>{

	@Autowired
	private NewsDao newsDao;
	
	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasNewsRequest();
	}

	@Override
	protected NewsRequest extractRequestMessage(MBSRequest request) {
		return request.getNewsRequest();
	}

	@Override
	protected void processMessage(NewsRequest request, Builder responseBuilder) {		
		NewsResponse.Builder builder = NewsResponse.newBuilder();
		
		Set<Integer> existingIds = new HashSet<Integer>(request.getNewsIdsList());		
		
		for (News news: newsDao.all()) {
			if (!existingIds.contains(news.getId()))
				builder.addNews(news.createMessage());
			existingIds.remove(news.getId());
		}
		
		builder.addAllDeletedIds(existingIds);		
		responseBuilder.setNewsResponse(builder.build());
		
	}

}
