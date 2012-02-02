package models.ubrr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.db.jpa.GenericModel;

@Entity
@Table(name="ida_card_products")
//uniqueConstraints=@UniqueConstraint(columnNames={"card_name"})
public class Product extends GenericModel {//extends UpdateTimeAwareModel{

//	{
//		ENTITY_NAME = "products";
//	}
	
	@Id
	@Column(name="card_name")
	public String id;
	
	public String getId() {
        return id;
    }

    @Override
    public Object _key() {
        return getId();
    }
}
