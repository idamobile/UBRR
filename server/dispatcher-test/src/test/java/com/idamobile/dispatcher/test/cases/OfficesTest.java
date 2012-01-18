package com.idamobile.dispatcher.test.cases;

import org.junit.Assert;
import org.junit.Test;

import com.idamobile.dispatcher.test.AbstractTestCase;
import com.idamobile.protocol.ubrr.Locations.OfficesRequest;
import com.idamobile.protocol.ubrr.Locations.OfficesResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public class OfficesTest extends AbstractTestCase{

	@Test
	public void testOfficesNow() throws Exception {
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		OfficesRequest.Builder oBuilder = OfficesRequest.newBuilder();
		oBuilder.setLastUpdateTime(System.currentTimeMillis());
		oBuilder.setPage(2);
		
		builder.setOfficesRequest(oBuilder);
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasOfficesResponse());
		
		OfficesResponse oResp = response.getOfficesResponse();		
		
		Assert.assertTrue(oResp.getOfficesCount() == 0);
		
	}

	@Test
	public void testOfficesNew() throws Exception {
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		OfficesRequest.Builder oBuilder = OfficesRequest.newBuilder();
		oBuilder.setLastUpdateTime(0L);
		oBuilder.setPage(2);
		
		builder.setOfficesRequest(oBuilder);
		MBSResponse response = call(builder);

		Assert.assertTrue(response.hasOfficesResponse());
		
		OfficesResponse oResp = response.getOfficesResponse();		

		Assert.assertTrue(oResp.getPage() == 2);
		Assert.assertTrue(oResp.getOfficesCount() > 0);
	}

	@Test
	public void testOfficesPageOutOfBounds() throws Exception {
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		OfficesRequest.Builder oBuilder = OfficesRequest.newBuilder();
		oBuilder.setLastUpdateTime(0L);
		oBuilder.setPage(1000);
		
		builder.setOfficesRequest(oBuilder);
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasOfficesResponse());
		
		OfficesResponse oResp = response.getOfficesResponse();		

		Assert.assertTrue(oResp.getPage() == 1000);
		Assert.assertTrue(oResp.getOfficesCount() == 0);
	}

	
	
}
