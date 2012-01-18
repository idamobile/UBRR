package com.idamobile.server.service.support;

import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

@Component
public class MessageProcessor {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MBSResponse handleRequest(MBSRequest request) {
		MBSResponse.Builder responseBuilder = MBSResponse.newBuilder();

		for (AbstractMessageService service: MessageServiceRegistry.getServices()) {
			if (service.isApplicable(request)) {
				service.processMessage(service.extractRequestMessage(request), responseBuilder);
			}
		}

		return responseBuilder.build();
	}

}
