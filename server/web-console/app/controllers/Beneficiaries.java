package controllers;

import java.util.List;

import models.core.Image;
import models.core.Image.Platform;
import models.core.Image.Resolution;
import models.custom.configuration.BeneficiaryMapping;
import models.custom.configuration.PaymentCategory;
import play.db.jpa.Blob;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Beneficiaries extends Controller{

	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            renderArgs.put("user", Security.connected());
        }
    }
	
	public static void list() {
		List<BeneficiaryMapping> bs = BeneficiaryMapping.all().fetch();		
		render(bs);
	}

	public static void benPopup(long id) {
		BeneficiaryMapping bf = BeneficiaryMapping.findById(id);
		render(bf);
	}
	
	public static void save(BeneficiaryMapping bf, Blob iosHdpi, Blob iosMdpi) {
    	
		String imageId = bf.imageId;
    	if (imageId == null) {    	
    		imageId = String.valueOf(System.currentTimeMillis()+bf.hashCode());
    	}

    	bf.imageId = imageId;
    	
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

        bf.save();
        list();
	}

	public static void delete(long id) {
		BeneficiaryMapping bf = BeneficiaryMapping.findById(id);
		if (bf != null) {
			bf.delete();
		}
		list();
	}
	
	public static void getImage(BeneficiaryMapping bf, Platform platform, Resolution resolution) {
    	Image image = bf.getImage(platform, resolution);
        if (image != null && image.image != null) {
	        response.setContentTypeIfNotSet(image.image.type());
	        renderBinary(image.image.get());
        }		
	}
	
	public static void getPreview(BeneficiaryMapping bf) {
    	Image image = bf.getPreview();
        if (image != null && image.image != null) {
	        response.setContentTypeIfNotSet(image.image.type());
	        renderBinary(image.image.get());
        }				
	}

	
}
