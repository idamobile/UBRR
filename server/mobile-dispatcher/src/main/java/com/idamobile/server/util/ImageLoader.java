package com.idamobile.server.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.idamobile.server.dao.core.PropertyDao;

@Component
public class ImageLoader {

	private static final String PROPERTY_KEY_PATH = "ida.attachments.path";
	
	private final Logger log = Logger.getLogger(ImageLoader.class);
	
	@Autowired
	private PropertyDao propertyDao;
	
	private String getAttachmentsPath() {
		return propertyDao.getProperty(PROPERTY_KEY_PATH);
	}
		
	public byte[] getImageBytes(String location) {
		try {
			if (location == null) {
				throw new IOException("Image location is null");
			}
			
			if (location.indexOf('|') == -1) {
				throw new IOException("Image location has wrong format");
			}
			
			location = location.substring(0, location.indexOf('|'));
			String filename = getAttachmentsPath() + location;
			
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			FileInputStream fin = new FileInputStream(filename);
			byte buf[] = new byte[1024];
			int n = 0;
			
			while ((n = fin.read(buf)) != -1) {
				bout.write(buf, 0, n);
			}
			
			return bout.toByteArray();
		}catch (IOException e) {
			log.error("Unable to read image", e);
			return new byte[0];
		}
	}
	
}
