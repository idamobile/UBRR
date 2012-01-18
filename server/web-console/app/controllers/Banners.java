package controllers;

import java.util.List;

import models.Banner;
import models.core.Image;
import models.core.Image.Platform;
import models.core.Image.Resolution;
import play.db.jpa.Blob;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Banners extends Controller{

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }
	
	public static void list() {
		List<Banner> banners = Banner.find("order by order").fetch();
		render(banners);
	}
	
	public static void add(long bannerId) {
		Banner banner = Banner.findById(bannerId);
		render(banner);
	}
	
	public static void getImage(Banner banner, Platform platform, Resolution resolution) {
		if (banner == null) {
			return;
		}
		
    	Image image = banner.getImage(platform, resolution);
        if (image != null && image.image != null) {
	        response.setContentTypeIfNotSet(image.image.type());
	        renderBinary(image.image.get());
        }		
	}
	
	public static void getPreview(Banner banner) {
		Image image = banner.getPreview();
        if (image != null && image.image != null) {
	        response.setContentTypeIfNotSet(image.image.type());
	        renderBinary(image.image.get());
        }		
	}
	
    public static void save(Banner banner, Blob iosHdpi, Blob iosMdpi) {
       	if (banner.id != null) {
    		banner = Banner.findById(banner.id);
    	}else {
    		banner.order = Banner.count();
    	}
    	
    	String imageId = banner.imageId;
    	if (imageId == null || imageId.length() == 0) {    	
    		imageId = String.valueOf(System.currentTimeMillis()+banner.hashCode());
    	}
    	
    	banner.imageId = imageId;
    	
    	Image iosHdpiImg = Image.findExactOrCreate(imageId, Platform.iOS, Resolution.hdpi);
    	if (iosHdpi != null) {
    		iosHdpiImg.image = iosHdpi;
    	}
    	iosHdpiImg.save();
    	
    	Image iosMdpiImg = Image.findExactOrCreate(imageId, Platform.iOS, Resolution.mdpi);
    	if (iosMdpi != null) {
    		iosMdpiImg.image = iosMdpi;
    	}
    	iosMdpiImg.save();

        banner.save();
        list();
    }

    public static void delete(long id) {
        Banner banner = Banner.findById(id);
        
        List<Image> images = Image.find("byImageId", banner.imageId).fetch();
        for (Image image: images) {
        	image.delete();
        }        
    	banner.delete();    	
        list();
    }

    public static void edit(long id) {
        add(id);
    }
    
    public static void moveDown(long id) {    	
    	Banner banner = Banner.findById(id);
    	banner.moveDown();    	
    	list();
    }

    public static void moveUp(long id) {    	
    	Banner banner = Banner.findById(id);
    	banner.moveUp();    	
    	list();
    }
	
}
