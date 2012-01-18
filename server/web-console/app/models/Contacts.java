package models;

import java.util.List;
import java.util.Map;

import models.core.Contact;
import models.core.LastUpdate;

public class Contacts {

	private static final String ENTITY_NAME = "contacts";
	
	public static List<Contact> getContacts() {
		return Contact.find("order by order").fetch();
	}
	
	public static void saveContacts(Map<String, String> contacts) {
		boolean isDirty = false;
		for (String key: contacts.keySet()) {
			Contact contact = Contact.find("byContactId", key).first();
			if (contact != null) {
				contact.setValue(contacts.get(key));
				contact.save();
				isDirty = true;
			}			
		}
		
		if (isDirty) {
			saveLastUpdateTime();
		}
	}
	
	public static long getLastUpdateTime() {
		return LastUpdate.get(ENTITY_NAME);
	}

	public static void saveLastUpdateTime() {
		LastUpdate.setNow(ENTITY_NAME);
	}
	
}
