package models.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.db.jpa.Blob;
import play.db.jpa.Model;

@Entity
@Table(name="ida_images", uniqueConstraints=@UniqueConstraint(columnNames={"image_id", "platform", "resolution"}))
public class Image extends Model{
	
	public enum Platform {
		iOS, Android
	}
	
	public enum Resolution {
		mdpi, hdpi
	}
	
	@Column(name="image_id")
	public String imageId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="platform")
	public Platform platform;
	
	@Enumerated(EnumType.STRING)
	@Column(name="resolution")
	public Resolution resolution;
	
	@Column(name="image")
	public Blob image;

	public Image(String imageId, Platform platform, Resolution resolution,
			Blob image) {
		this.imageId = imageId;
		this.platform = platform;
		this.resolution = resolution;
		this.image = image;
	}
	
	public Image(String imageId, Platform platform, Resolution resolution) {
		this(imageId, platform, resolution, null);
	}

	
	public Image(String imageId) {
		this(imageId, null, null, null);
	}
	
	public static final Image findExact(String imageId, Platform platform, Resolution resolution) {
		return Image.find("byImageIdAndPlatformAndResolution", imageId, platform, resolution).first();
	}

	public static final Image findExactOrCreate(String imageId, Platform platform, Resolution resolution) {
		Image img = findExact(imageId, platform, resolution);		
		return img != null?img:new Image(imageId, platform, resolution);
	}

	
}
