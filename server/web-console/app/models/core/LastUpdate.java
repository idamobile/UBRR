package models.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * Stores last update time of all entities in the administration console.
 * Data should be updated manually in each entity class
 * @author zjor
 *
 */
@Entity
@Table(name="ida_last_updates", uniqueConstraints=@UniqueConstraint(columnNames={"entity_name"}))
public class LastUpdate extends Model{

	@Required
	@Column(name="entity_name")
	public String entityName;
	
	@Column(name="last_update_time")
	public Long lastUpdateTime;

	public LastUpdate(String entityName, Long lastUpdateTime) {
		this.entityName = entityName;
		this.lastUpdateTime = lastUpdateTime;
	}	

	public LastUpdate(String entityName) {
		this(entityName, 0L);
	}	
	
	/**
	 * Stores last update time for the entity
	 * @param entityName
	 * @param time
	 */
	public static void set(String entityName, Long time) {
		LastUpdate lu = LastUpdate.find("byEntityName", entityName).first();
		if (lu == null) {
			lu = new LastUpdate(entityName);
		}
		lu.lastUpdateTime = time;		
		lu.save();		
	}
	
	public static void setNow(String entityName) {
		set(entityName, new Date().getTime());
	}
	
	/**
	 * Retrieves last update time for the specified entity
	 * @param entityName
	 * @return
	 */
	public static long get(String entityName) {
		LastUpdate lu = LastUpdate.find("byEntityName", entityName).first();
		return lu == null? 0L: lu.lastUpdateTime;
	}
	
}
