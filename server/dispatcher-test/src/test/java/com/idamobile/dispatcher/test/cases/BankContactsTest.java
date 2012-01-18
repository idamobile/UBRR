package com.idamobile.dispatcher.test.cases;

import org.junit.Assert;
import org.junit.Test;

import com.idamobile.dispatcher.test.AbstractTestCase;
import com.idamobile.protocol.ubrr.Protocol.BankContactsRequest;
import com.idamobile.protocol.ubrr.Protocol.BankContactsResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;


public class BankContactsTest extends AbstractTestCase{
	
	@Test
	public void testBankContactsNow() throws Exception{
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		BankContactsRequest.Builder bcBuilder = BankContactsRequest.newBuilder();
		bcBuilder.setLastUpdateTime(System.currentTimeMillis());
		
		builder.setBankContactsRequest(bcBuilder.build());
		
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasBankContactsResponse());
		
		BankContactsResponse bcResponse = response.getBankContactsResponse();
				
		Assert.assertTrue(bcResponse.getLastUpdateTime() > 0);
		
		Assert.assertTrue(bcResponse.getContactsCount() == 0);		
	}
	
	@Test
	public void testBankContactsNew() throws Exception{
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		BankContactsRequest.Builder bcBuilder = BankContactsRequest.newBuilder();
		bcBuilder.setLastUpdateTime(0L);
		
		builder.setBankContactsRequest(bcBuilder.build());
		
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasBankContactsResponse());
		
		BankContactsResponse bcResponse = response.getBankContactsResponse();
				
		Assert.assertTrue(bcResponse.getLastUpdateTime() > 0);
		
		Assert.assertTrue(bcResponse.getContactsCount() > 0);		
	}
	
}
