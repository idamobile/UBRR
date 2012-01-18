package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.core.Image;
import models.core.LastUpdate;
import models.core.Image.Platform;
import models.core.Image.Resolution;
import models.core.OrderedEntity;

import play.db.jpa.Blob;
import play.db.jpa.JPABase;
import play.db.jpa.Model;

@Entity
@Table(name="ida_banners")
public class Banner extends OrderedEntity{

	{
		ENTITY_NAME = "banners";
	}
	
	@Column(name="title")
	public String title;
	
	@Column(name="url")
	public String url;
	
	@Column(name="text")
	public String text;
		
	@Column(name="image_id")
	public String imageId;
	
	public Image getImage(Platform platform, Resolution resolution) {
		return Image.findExact(imageId, platform, resolution);
	}
	
	public Image getPreview() {
		return Image.find("byImageId", imageId).first();
	}

	@Override
	protected OrderedEntity findSelf() {
		return Banner.findById(id);
	}

	@Override
	protected OrderedEntity findPrevious(OrderedEntity self) {
		return Banner.find("select b from Banner b where b.order <= ? and b.id <> ? order by b.order desc", self.order, self.id).first();
	}

	@Override
	protected OrderedEntity findSuccessive(OrderedEntity self) {
		return Banner.find("select b from Banner b where b.order >= ? and b.id <> ? order by b.order desc", self.order, self.id).first();
	}
			
}
