package com.idamobile.dispatcher.test.cases;

import org.junit.Assert;
import org.junit.Test;

import com.idamobile.dispatcher.test.AbstractTestCase;
import com.idamobile.protocol.ubrr.Commons.PairMessage;
import com.idamobile.protocol.ubrr.Commons.ResultCode;
import com.idamobile.protocol.ubrr.Protocol.EmailType;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;
import com.idamobile.protocol.ubrr.Protocol.SendEmailRequest;

public class SendEmailTest extends AbstractTestCase{

	@Test
	public void testSendEmail() throws Exception {
		MBSRequest.Builder builder = MBSRequest.newBuilder();
		
		SendEmailRequest.Builder mailReqBuilder = SendEmailRequest.newBuilder();
		
		mailReqBuilder.setEmailType(EmailType.FEEDBACK);
		mailReqBuilder.addItems(PairMessage.newBuilder()
				.setKey("feedback")
				.addValues("cool stuff"));
		mailReqBuilder.addItems(PairMessage.newBuilder()
				.setKey("client.name")
				.addValues("Mike"));
		mailReqBuilder.addItems(PairMessage.newBuilder()
				.setKey("client.last_name")
				.addValues("Simpson"));
		
		builder.setSendEmailRequest(mailReqBuilder.build());
		
		MBSResponse response = call(builder);
		
		Assert.assertTrue(response.hasSendEmailResponse());
		
		Assert.assertTrue(response.getSendEmailResponse().getCode().equals(ResultCode.SUCCESS));
	}
}
