package models.custom.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.core.Image;
import models.core.UpdateTimeAwareModel;
import models.core.Image.Platform;
import models.core.Image.Resolution;

import play.db.jpa.Model;

@Entity
@Table(name="ida_hc_product_types")
public class ProductTypeMapping extends UpdateTimeAwareModel{

	{
		ENTITY_NAME = "productTypes";
	}
	
	@Column(name="internal_name")
	public String internalName;
	
	@Column(name="visible_name_en")
	public String visibleName_EN;

	@Column(name="visible_name_ru")
	public String visibleName_RU;
	
	@Column(name="image_id")
	public String imageId;

	public Image getImage(Platform platform, Resolution resolution) {
		return Image.findExact(imageId, platform, resolution);
	}
	
	public Image getPreview() {
		return Image.find("byImageId", imageId).first();
	}
	
}
