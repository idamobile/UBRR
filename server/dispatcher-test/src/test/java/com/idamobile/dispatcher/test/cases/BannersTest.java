package com.idamobile.dispatcher.test.cases;

import org.junit.Assert;
import org.junit.Test;

import com.idamobile.dispatcher.test.AbstractTestCase;
import com.idamobile.protocol.ubrr.Banners.BannersRequest;
import com.idamobile.protocol.ubrr.Banners.BannersResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public class BannersTest extends AbstractTestCase{
	
	@Test
	public void testBannersNow() throws Exception{
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		BannersRequest.Builder bBuilder = BannersRequest.newBuilder();		
		bBuilder.setLastUpdateTime(System.currentTimeMillis());
		
		builder.setBannersRequest(bBuilder.build());
		
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasBannersResponse());
		
		BannersResponse bResp = response.getBannersResponse();
		
		Assert.assertTrue(bResp.getLastUpdateTime() > 0);
		Assert.assertTrue(bResp.getBannersCount() == 0);
	}

	@Test
	public void testBannersNew() throws Exception{
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		BannersRequest.Builder bBuilder = BannersRequest.newBuilder();		
		bBuilder.setLastUpdateTime(0L);
		
		builder.setBannersRequest(bBuilder.build());
		
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasBannersResponse());
		
		BannersResponse bResp = response.getBannersResponse();
		
		Assert.assertTrue(bResp.getLastUpdateTime() > 0);
		Assert.assertTrue(bResp.getBannersCount() > 0);
	}
	
	
}
