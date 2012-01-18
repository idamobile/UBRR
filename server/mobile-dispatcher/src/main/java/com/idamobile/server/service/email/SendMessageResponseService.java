package com.idamobile.server.service.email;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.idamobile.protocol.ubrr.Commons.PairMessage;
import com.idamobile.protocol.ubrr.Commons.ResultCode;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.SendEmailRequest;
import com.idamobile.protocol.ubrr.Protocol.SendEmailResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.dao.core.EmailConfigDao;
import com.idamobile.server.model.EmailConfig;
import com.idamobile.server.quartz.QuartzManager;
import com.idamobile.server.service.support.AbstractMessageService;
import com.idamobile.server.util.SpringApplicationContext;
import com.jamesmurty.utils.XMLBuilder;

@Component
public class SendMessageResponseService extends AbstractMessageService<SendEmailRequest>{

	private static final String VARIABLE_UNDEFINED = "[UNDEFINED]";
	private static final String VARIABLE_REGEX = "\\$\\{([A-Za-z0-9\\._-]+)\\}";
	
	private static final Logger log = Logger.getLogger(SendMessageResponseService.class);
	
	@Value("${mail.smtp.host}")
	private String host;
	
	@Value("${mail.smtp.port}")
	private int port;
	
	@Value("${mail.smtp.user}")
	private String username;
	
	@Value("${mail.smtp.pass}")
	private String password;
	
	@Value("${mail.smtp.use.ssl}")
	private boolean useSsl;
	
	@Value("${mail.smtp.sender.email}")
	private String fromEmail;
	
	@Value("${mail.smtp.sender.name}")
	private String fromName;
	
	@Autowired
	private EmailConfigDao configDao;
		
	@Autowired
	private QuartzManager quartzManager;
	
	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasSendEmailRequest();
	}

	@Override
	protected SendEmailRequest extractRequestMessage(MBSRequest request) {
		return request.getSendEmailRequest();
	}

	@Override
	protected void processMessage(SendEmailRequest request, Builder responseBuilder) {
		
		SendEmailResponse.Builder builder = SendEmailResponse.newBuilder();
		
		int number = request.getEmailType().getNumber();
		log.debug("EmailType: " + number);
		EmailConfig emailConfig = configDao.get(number);
		
		try {

			JobDataMap args = new JobDataMap();
			args.put(SendEmailJob.ARG_KEY_SMTP_HOST, host);
			args.put(SendEmailJob.ARG_KEY_SMTP_PORT, port);
			args.put(SendEmailJob.ARG_KEY_SMTP_USER, username);
			args.put(SendEmailJob.ARG_KEY_SMTP_PASS, password);
			args.put(SendEmailJob.ARG_KEY_FROM_EMAIL, fromEmail);
			args.put(SendEmailJob.ARG_KEY_FROM_NAME, fromName);
			args.put(SendEmailJob.ARG_KEY_SMTP_USE_SSL, useSsl);
			
			args.put(SendEmailJob.ARG_KEY_RECIPIENTS, emailConfig.getRecipients());
			args.put(SendEmailJob.ARG_KEY_SUBJECT, processSubject(request.getItemsList(), emailConfig.getSubject()));
			args.put(SendEmailJob.ARG_KEY_MESSAGE, processMessage(request.getItemsList(), emailConfig.getXsltPath()));
			
			JobDetail jobDetail = JobBuilder.newJob(SendEmailJob.class)
				.withIdentity(UUID.randomUUID().toString())
				.usingJobData(args)
				.build();
			
			Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(UUID.randomUUID().toString())
				.startNow()
				.build();
			
			quartzManager.getScheduler().scheduleJob(jobDetail, trigger);
						
			builder.setCode(ResultCode.SUCCESS);
			
		}catch (Exception e) {
			
			builder.setCode(ResultCode.FAILURE);
			builder.setErrorMessage(""+e);
			
			e.printStackTrace();
		} finally {
			responseBuilder.setSendEmailResponse(builder.build());
		}
		
	}

	private String processSubject(List<PairMessage> message, String input) {

		log.debug("Processing subject: "+input);
		
		Pattern p = Pattern.compile(VARIABLE_REGEX);
		Matcher m = p.matcher(input);

		Map<String, String> args = new HashMap<String, String>();
		for (PairMessage pair: message) {
			if (pair.getValuesCount() > 0) {
				args.put(pair.getKey(), pair.getValues(0));
			}
		}
		
		while (m.find()) {
			String value = args.get(m.group(1));
			
			input = m.replaceFirst(value == null ? VARIABLE_UNDEFINED : value);
			m = p.matcher(input);
		}
	
		log.debug("Resulting subject: "+input);
		return input;
	}

	private String processMessage(List<PairMessage> message, String xsltPathConfig) throws Exception{
		XMLBuilder xmlBuilder = XMLBuilder.create("message");
		for (PairMessage pair: message) {
			XMLBuilder root = xmlBuilder
				.e("item")
					.e("key")	
						.t(pair.getKey())
					.up()
					.e("values");
			for (String value: pair.getValuesList()) {
				root.e("value").t(value);
			}
		}

		InputStream xsltStream = null;
		
		File xsltPath = new File(xsltPathConfig == null?StringUtils.EMPTY:xsltPathConfig);
		if (xsltPath.exists() && xsltPath.isFile()) {
			xsltStream = new FileInputStream(xsltPath);
		}else {
			xsltStream = SpringApplicationContext.getResource("classpath:xsl/default.xsl").getInputStream();
		}
		
		TransformerFactory tFactory = TransformerFactory.newInstance();		
		Transformer tf = tFactory.newTransformer(new StreamSource(xsltStream));		
		ByteArrayOutputStream out = new ByteArrayOutputStream();		
		tf.transform(new DOMSource(xmlBuilder.getDocument()), new StreamResult(out));
		
		String messageBody = new String(out.toByteArray());
		
		log.debug("Message body: "+messageBody);
		
		return messageBody;		
	}
	
}
