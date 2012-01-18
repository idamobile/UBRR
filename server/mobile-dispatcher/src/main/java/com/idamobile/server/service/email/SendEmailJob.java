package com.idamobile.server.service.email;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SendEmailJob implements Job{
	
	private static final String CONTACT_DELIMETERS = ";,";
	private static final String EMAIL_REGEX = "([A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4})";
	private static final String NAME_REGEX = "['\"]([A-Za-z0-9\\s]+)['\"]";

	public static final String ARG_KEY_RECIPIENTS = "arg.key.recipients";
	public static final String ARG_KEY_SUBJECT = "arg.key.subject";
	public static final String ARG_KEY_MESSAGE = "arg.key.message";
	
	public static final String ARG_KEY_SMTP_HOST = "arg.key.smtp.host";
	public static final String ARG_KEY_SMTP_PORT = "arg.key.smtp.port";
	public static final String ARG_KEY_SMTP_USER = "arg.key.smtp.user";
	public static final String ARG_KEY_SMTP_PASS = "arg.key.smtp.pass";
	public static final String ARG_KEY_SMTP_USE_SSL = "arg.key.smtp.use.ssl";
	
	public static final String ARG_KEY_FROM_NAME = "arg.key.from.name";
	public static final String ARG_KEY_FROM_EMAIL = "arg.key.from.email";


	private static final Logger log = Logger.getLogger(SendEmailJob.class);
	
	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		JobDataMap args = ctx.getJobDetail().getJobDataMap();
		
		try {		
			SimpleEmail email = new SimpleEmail();
			email.setCharset("UTF8");
			
			addRecipients(email, args.getString(ARG_KEY_RECIPIENTS));
			email.setSubject(args.getString(ARG_KEY_SUBJECT));
			email.setMsg(args.getString(ARG_KEY_MESSAGE));
			
			email.setHostName(args.getString(ARG_KEY_SMTP_HOST));
			email.setSmtpPort(args.getInt(ARG_KEY_SMTP_PORT));
			
			String username = args.getString(ARG_KEY_SMTP_USER);
			String password = args.getString(ARG_KEY_SMTP_PASS);
			if (username != null && username.trim().length() > 0) {
				email.setAuthenticator(new DefaultAuthenticator(username, password));
			}
			email.setSSL(args.getBoolean(ARG_KEY_SMTP_USE_SSL));
			
			email.setFrom(args.getString(ARG_KEY_FROM_EMAIL), args.getString(ARG_KEY_FROM_NAME));
			
			log.debug("Trying to send message");
			
			email.send();
			
			log.debug("Message was sent successfully");
			
		}catch (EmailException e) {
			log.error("Failed to send e-mail", e);
		}		
		
	}
	
	private void addRecipients(SimpleEmail email, String recipients) throws EmailException {
		log.debug("Processing recipients: "+recipients);
		
		Pattern namePattern = Pattern.compile(NAME_REGEX);
		Pattern emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		
		StringTokenizer tokenizer = new StringTokenizer(recipients, CONTACT_DELIMETERS);
		while (tokenizer.hasMoreTokens()) {

			String token = tokenizer.nextToken().trim();
			if (token.length() == 0) {
				continue;
			}

			String name = "";
			String emailAddr = "";
			
			Matcher m = namePattern.matcher(token);
			if (m.find()) {
				name = m.group(1);
			}
			m = emailPattern.matcher(token);
			if (m.find()) {
				emailAddr = m.group(1);
			}
			
			log.debug(String.format("Adding Name: %s; E-mail: %s", name, emailAddr));
			
			email.addTo(emailAddr, name);
		}				
		
	}

}
