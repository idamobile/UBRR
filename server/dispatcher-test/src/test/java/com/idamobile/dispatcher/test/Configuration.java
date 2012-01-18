package com.idamobile.dispatcher.test;

public class Configuration {
	
	public static final String REMOTE_SERVER_URL = "http://project.idamob.ru:8000/idaserver-UBRR/request/";
	public static final String LOCAL_SERVER_URL = "http://localhost:8000/idaserver-UBRR/request/";
	
	private static final boolean isLocal = true;
	
	public static String getServerUrl() {
		return isLocal?LOCAL_SERVER_URL:REMOTE_SERVER_URL;
	}
	
}
