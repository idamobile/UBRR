package models.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.junit.Ignore;

import controllers.CRUD.Hidden;

import play.data.validation.Match;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.i18n.Messages;

@Entity
@Table(name="ida_contacts", uniqueConstraints=@UniqueConstraint(columnNames={"contact_id"}))
public class Contact extends OrderedEntity{
	
	public static final String PREFIX = "ida.";
	
	public enum ContactType{
		URL, PHONE, LOB;		
	}
	@Required
	@Match(value="ida.[a-z.]*")
	@Column(name="contact_id")
	public String contactId;
	
	@Required
	@Column(name="contact_name")
	public String name;
	
	@Required
	@Enumerated(EnumType.STRING)
	@Column(name="contact_type")	
	public ContactType type;
	
	@Hidden
	@Column(name="value")	
	public String value;
	
	@Hidden
	@Column(name="large_value", length=1024)
	public String largeValue;
		
	public Contact() {		
		this.order = 0L;
	}
	
	public Contact(String contactId, String name, ContactType type, String value,
			String largeValue) {
		this();
		this.contactId = contactId;
		this.name = name;
		this.type = type;
		this.value = value;
		this.largeValue = largeValue;		
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)[%s]", name, contactId, type);
	}	
	
	public String getLocalizedName() {
		return Messages.get(name);
	}
	
	private boolean isLOB() {
		return ContactType.LOB.equals(type);
	}
	
	/**
	 * Retrieves value considering whether it is a large value
	 * @return
	 */
	public String getValue() {
		return isLOB()?largeValue:value;
	}

	/**
	 * Sets value considering its type
	 * @param value
	 */
	public void setValue(String value) {
		if (isLOB()) {
			this.largeValue = value;
		}else {
			this.value = value;
		}
	}
	
	@Override
	protected OrderedEntity findSelf() {		
		return Contact.find("byContactId", contactId).first();
	}

	@Override
	protected OrderedEntity findPrevious(OrderedEntity self) {
		return Contact.find("select c from Contact c where c.order <= ? and c.id <> ? order by c.order desc", self.order, self.id).first();
	}

	@Override
	protected OrderedEntity findSuccessive(OrderedEntity self) {
		return Contact.find("select c from Contact c where c.order >= ? and c.id <> ? order by c.order desc", self.order, self.id).first();
	}
	
}
