package controllers;

import java.util.List;

import models.core.Image;
import models.core.Image.Platform;
import models.core.Image.Resolution;
import models.custom.configuration.DiscountCategory;
import play.db.jpa.Blob;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class DiscountCategories extends Controller {
	
	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }
	
	public static void list() {
		List<DiscountCategory> categories = DiscountCategory.all().fetch();		
		render(categories);
	}
	
	public static void categoryPopup(long id) {
		DiscountCategory category = DiscountCategory.findById(id);
		render(category);
	}
	
	public static void save(DiscountCategory category, Blob iosHdpi, Blob iosMdpi) {
    	
		String imageId = category.imageId;
    	if (imageId == null) {    	
    		imageId = String.valueOf(System.currentTimeMillis()+category.hashCode());
    	}

    	category.imageId = imageId;
    	
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

        category.save();
        list();
	}

	public static void delete(long id) {
		DiscountCategory category = DiscountCategory.findById(id);
		if (category != null) {
			category.delete();
		}
		list();
	}
	
	public static void getImage(DiscountCategory category, Platform platform, Resolution resolution) {
    	Image image = category.getImage(platform, resolution);
        if (image != null && image.image != null) {
	        response.setContentTypeIfNotSet(image.image.type());
	        renderBinary(image.image.get());
        }		
	}
	
	public static void getPreview(DiscountCategory category) {
    	Image image = category.getPreview();
        if (image != null && image.image != null) {
	        response.setContentTypeIfNotSet(image.image.type());
	        renderBinary(image.image.get());
        }				
	}

}
