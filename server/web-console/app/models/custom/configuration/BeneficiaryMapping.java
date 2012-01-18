package models.custom.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.core.Image;
import models.core.UpdateTimeAwareModel;
import models.core.Image.Platform;
import models.core.Image.Resolution;

@Entity
@Table(name="ida_hc_beneficiaries")
public class BeneficiaryMapping extends UpdateTimeAwareModel{
	
	{
		ENTITY_NAME = "beneficiaries";
	}
	
	@Column(name="beneficiary_id")
	public String beneficiaryId;
	
	@Column(name="image_id")
	public String imageId;

	public BeneficiaryMapping() {}
	
	public BeneficiaryMapping(String beneficiaryId, 
			String imageId) {
		this.beneficiaryId = beneficiaryId;
		this.imageId = imageId;
	}

	public Image getImage(Platform platform, Resolution resolution) {
		return Image.findExact(imageId, platform, resolution);
	}
	
	public Image getPreview() {
		return Image.find("byImageId", imageId).first();
	}
	
}
