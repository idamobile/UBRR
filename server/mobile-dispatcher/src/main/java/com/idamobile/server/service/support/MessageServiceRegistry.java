package com.idamobile.server.service.support;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds list of all message services
 * 
 * @author zjor
 * 
 */
@SuppressWarnings("rawtypes")
public class MessageServiceRegistry {
	
	private static final List<AbstractMessageService> services = new ArrayList<AbstractMessageService>();

	public static void addService(AbstractMessageService service) {
		services.add(service);
	}
	
	public static List<AbstractMessageService> getServices() {
		return services;
	}

}
