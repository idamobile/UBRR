package com.idamobile.server.model;

public class EmailConfig {

	public static final EmailConfig DEFAULT_CONFIG = new EmailConfig(-1, "user@example.com", "test subject", "default xslt");
	
	private int emailType;
	
	private String recipients;
	
	private String subject;
	
	private String xsltPath;

	public EmailConfig() {
		
	}
	
	public EmailConfig(int emailType, String recipients, String subject, String xsltPath) {
		this.emailType = emailType;
		this.recipients = recipients;
		this.subject = subject;
		this.xsltPath = xsltPath;
	}

	public int getEmailType() {
		return emailType;
	}

	public void setEmailType(int emailType) {
		this.emailType = emailType;
	}

	public String getRecipients() {
		return recipients;
	}

	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getXsltPath() {
		return xsltPath;
	}

	public void setXsltPath(String xsltPath) {
		this.xsltPath = xsltPath;
	}
		
}
