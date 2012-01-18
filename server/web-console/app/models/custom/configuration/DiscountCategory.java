package models.custom.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.core.Image;
import models.core.UpdateTimeAwareModel;
import models.core.Image.Platform;
import models.core.Image.Resolution;

@Entity
@Table(name="ida_hc_discount_categories")
public class DiscountCategory extends UpdateTimeAwareModel{
	
	{
		ENTITY_NAME="discount.categories";
	}
	
	@Column(name="category_id")
	public int categoryId;
	
	@Column(name="category_name")
	public String name;
	
	@Column(name="image_id")
	public String imageId;
	
	public Image getImage(Platform platform, Resolution resolution) {
		return Image.findExact(imageId, platform, resolution);
	}
	
	public Image getPreview() {
		return Image.find("byImageId", imageId).first();
	}

}
