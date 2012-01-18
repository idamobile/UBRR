package com.idamobile.dispatcher.test;

import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public abstract class AbstractTestCase {

	protected MBSResponse call(MBSRequest.Builder builder) throws Exception {
		IdaServerClient client = new IdaServerClient(Configuration.getServerUrl());
		
		return client.call(builder.build());		
	}
}
