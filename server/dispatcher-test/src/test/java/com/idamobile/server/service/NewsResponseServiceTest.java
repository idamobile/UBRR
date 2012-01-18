package com.idamobile.server.service;

import static org.junit.Assert.*;
import org.junit.Test;

import com.idamobile.dispatcher.test.AbstractTestCase;
import com.idamobile.protocol.ubrr.News.NewsRequest;
import com.idamobile.protocol.ubrr.News.NewsResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public class NewsResponseServiceTest extends AbstractTestCase{
	
	@Test
	public void testNews() throws Exception {
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		builder.setNewsRequest(NewsRequest.newBuilder());
		
		MBSResponse response = call(builder);
		
		assertTrue(response.hasNewsResponse());
		
		NewsResponse news = response.getNewsResponse();
		
		assertTrue(news.getDeletedIdsCount() == 0);		
		assertTrue(news.getNewsCount() > 0);				
	}
	
	@Test
	public void testDeletedNews() throws Exception {
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		builder.setNewsRequest(NewsRequest
				.newBuilder()
				.addNewsIds(1000));
		
		MBSResponse response = call(builder);
		
		assertTrue(response.hasNewsResponse());
		
		NewsResponse news = response.getNewsResponse();
		
		assertTrue(news.getDeletedIdsCount() > 0);		
		assertTrue(news.getNewsCount() > 0);						
	}

}
