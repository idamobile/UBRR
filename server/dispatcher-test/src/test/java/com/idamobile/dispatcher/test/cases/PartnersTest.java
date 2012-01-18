package com.idamobile.dispatcher.test.cases;

import org.junit.Assert;
import org.junit.Test;

import com.idamobile.dispatcher.test.AbstractTestCase;
import com.idamobile.protocol.ubrr.Commons.GeoPointMessage;
import com.idamobile.protocol.ubrr.Partners.NearestPartnerRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public class PartnersTest extends AbstractTestCase{

	@Test
	public void testNearestPartner() throws Exception {
		MBSRequest.Builder request = MBSRequest.newBuilder();
		
		request
			.setNearestPartnerRequest(
					NearestPartnerRequest
						.newBuilder().setLocation(
								GeoPointMessage
									.newBuilder()
										.setLatitude(55.6)
										.setLongitude(37.4)));
		MBSResponse response = call(request);
		
		Assert.assertTrue(response.hasNearestPartnerResponse());
		Assert.assertTrue(response.getNearestPartnerResponse().hasPartner());
		
	}
	
}
