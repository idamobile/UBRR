package models.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.db.jpa.Model;

@Entity
@Table(name="ida_properties", uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class Property extends Model{

	public static final String PROPERTY_PREFIX = "ida.";
	public static final String ATTACHMENTS_PATH = "ida.attachments.path";
	public static final String INT_STORAGE_PATH = "ida.int.storage.path";
	
	@Column(name="name")
	public String name;
	
	@Column(name="value")
	public String value;
	
	public Property(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public Property(String name) {
		this(name, null);
	}

	public static void set(String name, String value) {
		models.core.Property p = find("byName", name).first();
		if (p == null) {
			p = new models.core.Property(name);
		}
		p.value = value;
		p.save();		
	}
	
	public static String get(String name) {
		models.core.Property p = models.core.Property.find("byName", name).first();
		if (p != null) {
			return p.value;
		}else {
			return "";
		}
	}
	
}
