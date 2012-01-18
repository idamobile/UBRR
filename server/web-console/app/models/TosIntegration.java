package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.db.jpa.Model;

/**
 * Contains information about Talend Open Studio integration jobs
 * @author zjor
 *
 */
@Entity
@Table(name="ida_tos_integrations", uniqueConstraints=@UniqueConstraint(columnNames={"integration"}))
public class TosIntegration extends Model{

	@Column(name="integration")
	public String integration;
	
	@Column(name="url")
	public String url;
	
	@Column(name="cron_schedule")
	public String cronSchedule;
	
}
