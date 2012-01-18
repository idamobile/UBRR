package models.custom.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import models.core.Image;
import models.core.UpdateTimeAwareModel;
import models.core.Image.Platform;
import models.core.Image.Resolution;
import play.db.jpa.Model;

@Entity
@Table(name="ida_hc_payment_categories", uniqueConstraints=@UniqueConstraint(columnNames={"category"}))
public class PaymentCategory extends UpdateTimeAwareModel{
	
	{
		ENTITY_NAME = "payment.categories";
	}
	
	@Column(name="category")
	public String categoryId;
	
	@Column(name="image_id")
	public String imageId;

	public PaymentCategory() { }
	
	public PaymentCategory(String categoryId, String imageId) {
		this.categoryId = categoryId;
		this.imageId = imageId;
	}

	public Image getImage(Platform platform, Resolution resolution) {
		return Image.findExact(imageId, platform, resolution);
	}
	
	public Image getPreview() {
		return Image.find("byImageId", imageId).first();
	}

}
