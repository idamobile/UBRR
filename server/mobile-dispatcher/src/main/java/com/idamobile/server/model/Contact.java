package com.idamobile.server.model;

import java.util.HashMap;
import java.util.Map;

import com.idamobile.protocol.ubrr.Protocol;
import com.idamobile.protocol.ubrr.Protocol.ContactMessage;
import com.idamobile.protocol.ubrr.Protocol.ContactType;

/**
 * Represents entity stored in the IDA_CONTACTS table
 * @author zjor
 *
 */
public class Contact {

	public static final String TYPE_PHONE = "phone";
	public static final String TYPE_EMAIL = "email";
	public static final String TYPE_URL = "url";
	public static final String TYPE_LOB = "lob";

	@SuppressWarnings("serial")
	public static final Map<String, Protocol.ContactType> types = new HashMap<String, Protocol.ContactType>() {
		{
			put(TYPE_EMAIL, ContactType.EMAIL);
			put(TYPE_PHONE, ContactType.PHONE);
			put(TYPE_URL, ContactType.URL);
			put(TYPE_LOB, ContactType.LOB);
		}
	};
	
	/**
	 * Unique identifier of the contact record
	 */
	private String key;
	
	private String type;
	
	private String value;

	public Contact() {
		
	}
	
	public Contact(String key, String type, String value) {
		this.key = key;
		this.type = type.toLowerCase();
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.toLowerCase();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private ContactType getContactType() {
		if (types.containsKey(type)) {
			return types.get(type);
		}else {
			return ContactType.UNKNOWN;
		}
	}
	
	public ContactMessage createMessage() {
		ContactMessage.Builder builder = ContactMessage.newBuilder();
		if (key != null) {
			builder.setKey(key);
		}
		
		builder.setType(getContactType());
		
		if (value != null) {
			builder.setValue(value);
		}
		
		return builder.build();
	}
	
}
