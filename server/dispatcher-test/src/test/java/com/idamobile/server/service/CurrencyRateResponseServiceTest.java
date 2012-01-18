package com.idamobile.server.service;

import org.junit.Assert;
import org.junit.Test;

import com.idamobile.dispatcher.test.AbstractTestCase;
import com.idamobile.protocol.ubrr.Currency.CurrencyRateRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public class CurrencyRateResponseServiceTest extends AbstractTestCase{
	
	@Test
	public void testCurrencyRates() throws Exception {
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		builder.setCurrencyRequest(CurrencyRateRequest.newBuilder());
		
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasCurrencyResponse());
		
		Assert.assertTrue(response.getCurrencyResponse().getCurrencyCount() > 0);				
	}

}
