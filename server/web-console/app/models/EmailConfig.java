package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="ida_email_conf")
public class EmailConfig extends Model {
	
	@Column(name="email_type", unique=true)
	public int emailType;
	
	@Column(name="recipients")
	public String recipients;
	
	@Column(name="subject")
	public String subject;
	
	@Column(name="xslt_path")
	public String xsltPath;

}
