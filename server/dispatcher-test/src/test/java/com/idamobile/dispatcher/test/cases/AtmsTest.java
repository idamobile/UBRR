package com.idamobile.dispatcher.test.cases;

import org.junit.Assert;
import org.junit.Test;

import com.idamobile.dispatcher.test.AbstractTestCase;
import com.idamobile.protocol.ubrr.Locations.AtmsRequest;
import com.idamobile.protocol.ubrr.Locations.AtmsResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public class AtmsTest extends AbstractTestCase {
	
	@Test
	public void testAtmsNow() throws Exception {
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		AtmsRequest.Builder aBuilder = AtmsRequest.newBuilder();
		aBuilder.setLastUpdateTime(System.currentTimeMillis());
		aBuilder.setPage(2);
		
		builder.setAtmsRequest(aBuilder);
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasAtmsResponse());
		
		AtmsResponse oResp = response.getAtmsResponse();		
		
		Assert.assertTrue(oResp.getAtmsCount() == 0);
	}

	@Test
	public void testAtmsNew() throws Exception {
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		AtmsRequest.Builder aBuilder = AtmsRequest.newBuilder();
		aBuilder.setLastUpdateTime(0L);
		aBuilder.setPage(2);
		
		builder.setAtmsRequest(aBuilder);
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasAtmsResponse());
		
		AtmsResponse oResp = response.getAtmsResponse();		
		
		Assert.assertTrue(oResp.getPage() == 2);
		Assert.assertTrue(oResp.getAtmsCount() > 0);
	}
	
	@Test
	public void testAtmsOutOfBounds() throws Exception {
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		AtmsRequest.Builder aBuilder = AtmsRequest.newBuilder();
		aBuilder.setLastUpdateTime(0L);
		aBuilder.setPage(1000);
		
		builder.setAtmsRequest(aBuilder);
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasAtmsResponse());
		
		AtmsResponse oResp = response.getAtmsResponse();		
		
		Assert.assertTrue(oResp.getPage() == 1000);
		Assert.assertTrue(oResp.getAtmsCount() == 0);
	}
	

}
