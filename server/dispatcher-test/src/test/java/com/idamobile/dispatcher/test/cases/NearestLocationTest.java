package com.idamobile.dispatcher.test.cases;

import org.junit.Assert;
import org.junit.Test;

import com.idamobile.dispatcher.test.AbstractTestCase;
import com.idamobile.protocol.ubrr.Commons.GeoPointMessage;
import com.idamobile.protocol.ubrr.Locations.LocationType;
import com.idamobile.protocol.ubrr.Locations.NearestLocationRequest;
import com.idamobile.protocol.ubrr.Locations.NearestLocationResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public class NearestLocationTest extends AbstractTestCase {

	@Test
	public void testATM() throws Exception {
		MBSRequest.Builder request = MBSRequest.newBuilder();
		
    	request.setNearestLocationRequest(NearestLocationRequest
    			.newBuilder()
    			.setType(LocationType.ATM)
    			.setLocation(GeoPointMessage
    					.newBuilder()
    					.setLatitude(55)
    					.setLongitude(37)));
    	MBSResponse response = call(request);
    	
    	Assert.assertTrue(response.hasNearestLocationResponse());
    	
    	NearestLocationResponse nrs = response.getNearestLocationResponse();
    	Assert.assertTrue(nrs.hasNearestAtm());    	
	}

	@Test
	public void testOffice() throws Exception {
		MBSRequest.Builder request = MBSRequest.newBuilder();
		
    	request.setNearestLocationRequest(NearestLocationRequest
    			.newBuilder()
    			.setType(LocationType.OFFICE)
    			.setLocation(GeoPointMessage
    					.newBuilder()
    					.setLatitude(55)
    					.setLongitude(37)));
    	MBSResponse response = call(request);
    	
    	Assert.assertTrue(response.hasNearestLocationResponse());
    	
    	NearestLocationResponse nrs = response.getNearestLocationResponse();
    	Assert.assertTrue(nrs.hasNearestOffice());    	
	}
	
	@Test
	public void testBoth() throws Exception {
		MBSRequest.Builder request = MBSRequest.newBuilder();
		
    	request.setNearestLocationRequest(NearestLocationRequest
    			.newBuilder()
    			.setType(LocationType.ALL)
    			.setLocation(GeoPointMessage
    					.newBuilder()
    					.setLatitude(55)
    					.setLongitude(37)));
    	MBSResponse response = call(request);
    	
    	Assert.assertTrue(response.hasNearestLocationResponse());
    	
    	NearestLocationResponse nrs = response.getNearestLocationResponse();
    	Assert.assertTrue(nrs.hasNearestAtm() && nrs.hasNearestOffice());    	
	}
	
	@Test
	public void testNearest() throws Exception {
		MBSRequest.Builder request = MBSRequest.newBuilder();
		
    	request.setNearestLocationRequest(NearestLocationRequest
    			.newBuilder()
    			.setType(LocationType.NEAREST)
    			.setLocation(GeoPointMessage
    					.newBuilder()
    					.setLatitude(55)
    					.setLongitude(37)));
    	MBSResponse response = call(request);
    	
    	Assert.assertTrue(response.hasNearestLocationResponse());
    	
    	NearestLocationResponse nrs = response.getNearestLocationResponse();
    	Assert.assertTrue(nrs.hasNearestAtm() || nrs.hasNearestOffice());
	}	


}
