package com.idamobile.server.service.support;

import com.google.protobuf.GeneratedMessage;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

/**
 * Base class for all message types handlers
 * @author zjor
 *
 * @param <T>
 */
public abstract class AbstractMessageService<T extends GeneratedMessage> {

	public AbstractMessageService() {
		MessageServiceRegistry.addService(this);
	}
	
	/**
	 * Verifies whether it is a suitable handler for this message 
	 * @return
	 */
	protected abstract boolean isApplicable(MBSRequest request);
	
	/**
	 * Extracts request message of a certain type for this handler
	 * @param request
	 * @return
	 */
	protected abstract T extractRequestMessage(MBSRequest request);
	
	/**
	 * Performs request processing
	 * @param request
	 * @param responseBuilder
	 */
	protected abstract void processMessage(T request, MBSResponse.Builder responseBuilder);

}
