package controllers;

import java.util.List;

import models.Banner;
import models.core.Image;
import models.core.Image.Platform;
import models.core.Image.Resolution;
import models.custom.configuration.ProductTypeMapping;
import play.db.jpa.Blob;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class ProductTypes extends Controller{

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }
	
	public static void list() {
		List<ProductTypeMapping> mappings = ProductTypeMapping.all().fetch();
		render(mappings);
	}
		
	public static void typesPopup(long id) {
		ProductTypeMapping mapping = ProductTypeMapping.findById(id);
		render(mapping);
	}
	
	public static void save(ProductTypeMapping mapping, Blob iosHdpi, Blob iosMdpi) {
    	
		String imageId = mapping.imageId;
    	if (imageId == null) {    	
    		imageId = String.valueOf(System.currentTimeMillis()+mapping.hashCode());
    	}

    	mapping.imageId = imageId;
    	
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

        mapping.save();
        list();

	}
	
	public static void delete(long id) {
		ProductTypeMapping mapping = ProductTypeMapping.findById(id);
		if (mapping != null) {
			mapping.delete();
		}
		list();
	}

	public static void getImage(ProductTypeMapping mapping, Platform platform, Resolution resolution) {
    	Image image = mapping.getImage(platform, resolution);
        if (image != null && image.image != null) {
	        response.setContentTypeIfNotSet(image.image.type());
	        renderBinary(image.image.get());
        }		
	}
	
	public static void getPreview(ProductTypeMapping mapping) {
    	Image image = mapping.getPreview();
        if (image != null && image.image != null) {
	        response.setContentTypeIfNotSet(image.image.type());
	        renderBinary(image.image.get());
        }				
	}

}
