package com.idamobile.server.model.integration;

public class TosJobConfiguration {
	
	private String name;
	
	private String url;
	
	private String cronSchedule;

	public TosJobConfiguration(String name, String url, String cronSchedule) {
		this.name = name;
		this.url = url;
		this.cronSchedule = cronSchedule;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getCronSchedule() {
		return cronSchedule;
	}
	
}
