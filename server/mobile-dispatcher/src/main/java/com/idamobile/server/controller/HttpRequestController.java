package com.idamobile.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;
import com.idamobile.server.service.support.MessageProcessor;

/**
 * Handles requests for the application.
 */
@Controller
public class HttpRequestController {

	private static final String MIME_TYPE_PROTOBUF = "application/x-protobuf";

	private final Logger log = Logger.getLogger(HttpRequestController.class);

	@Autowired
	private MessageProcessor messageProcessor;
	
	@RequestMapping(value="/*")
	public String index() {
		return "index";
	}
	
	/**
	 * Handles requests from mobile client
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "request", method = RequestMethod.POST)
	public void processRequestMessage(HttpServletRequest request, HttpServletResponse response) {
		try {

			long start = System.nanoTime();
			MBSRequest requestMessage = MBSRequest.parseFrom(request
					.getInputStream());
			long estimate = (System.nanoTime() - start) / (int)1e6;
			log.info("Time to parse request: " + estimate + " ms");

			response.setContentType(MIME_TYPE_PROTOBUF);

			start = System.nanoTime();
			MBSResponse responseMessage = messageProcessor
					.handleRequest(requestMessage);
			estimate = (System.nanoTime() - start) / (int)1e6;
			log.info("Time to handle request: " + estimate + " ms");

			responseMessage.writeTo(response.getOutputStream());

		} catch (IOException ex) {
			log.error("Error to processing request", ex);
		}
	}
	
}
