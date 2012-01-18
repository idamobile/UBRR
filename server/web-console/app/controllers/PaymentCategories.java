package controllers;

import java.util.List;

import models.core.Image;
import models.core.Image.Platform;
import models.core.Image.Resolution;
import models.custom.configuration.PaymentCategory;
import models.custom.configuration.ProductStatusMapping;
import models.custom.configuration.ProductTypeMapping;
import play.db.jpa.Blob;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class PaymentCategories extends Controller {
	
	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }
	
	public static void list() {
		List<PaymentCategory> categories = PaymentCategory.all().fetch();		
		render(categories);
	}
	
	public static void categoryPopup(long id) {
		PaymentCategory category = PaymentCategory.findById(id);
		render(category);
	}
	
	public static void save(PaymentCategory category, Blob iosHdpi, Blob iosMdpi) {
    	
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
		PaymentCategory category = PaymentCategory.findById(id);
		if (category != null) {
			category.delete();
		}
		list();
	}
	
	public static void getImage(PaymentCategory category, Platform platform, Resolution resolution) {
    	Image image = category.getImage(platform, resolution);
        if (image != null && image.image != null) {
	        response.setContentTypeIfNotSet(image.image.type());
	        renderBinary(image.image.get());
        }		
	}
	
	public static void getPreview(PaymentCategory category) {
    	Image image = category.getPreview();
        if (image != null && image.image != null) {
	        response.setContentTypeIfNotSet(image.image.type());
	        renderBinary(image.image.get());
        }				
	}

}
