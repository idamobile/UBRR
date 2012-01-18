package models.custom.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.core.LastUpdate;
import models.core.UpdateTimeAwareModel;

import play.db.jpa.JPABase;
import play.db.jpa.Model;

@Entity
@Table(name="ida_hc_statuses", uniqueConstraints=@UniqueConstraint(columnNames={"product_kind", "status_key"}))
public class ProductStatusMapping extends UpdateTimeAwareModel{

	{
		ENTITY_NAME = "statuses";
	}
	
	@Column(name="product_kind")
	public int productKind;
	
	@Column(name="status_key")
	public String statusKey;
	
	@Column(name="status_value_en")
	public String statusValue_EN;

	@Column(name="status_value_ru")
	public String statusValue_RU;
	
}
