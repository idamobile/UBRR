package com.idamobile.server.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.protobuf.ByteString;
import com.idamobile.protocol.ubrr.Commons.Platform;
import com.idamobile.protocol.ubrr.Commons.Resolution;
import com.idamobile.protocol.ubrr.Protocol.ImageMessage;
import com.idamobile.protocol.ubrr.Protocol.ImageRequest;
import com.idamobile.protocol.ubrr.Protocol.ImageResponse;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse.Builder;
import com.idamobile.server.dao.core.ImageDao;
import com.idamobile.server.service.support.AbstractMessageService;

@Component
public class ImageResponseService extends AbstractMessageService<ImageRequest>{

	@Autowired
	private ImageDao imageDao;
	
	@Override
	public void processMessage(ImageRequest request, Builder responseBuilder) {
		
		ImageResponse.Builder builder = ImageResponse.newBuilder();
		
		Platform platform = request.hasPlatform()?request.getPlatform():Platform.iOS;
		Resolution resolution = request.hasResolution()?request.getResolution():Resolution.HDPI;
		
		String imageIds[] = new String[request.getImageIdsCount()];
		for (int i = 0; i<request.getImageIdsCount(); i++) {
			imageIds[i] = request.getImageIds(i);
		}
				
		Map<String, byte[]> map = imageDao.get(platform, resolution, imageIds);
		
		for (String key: map.keySet()) {
			ImageMessage.Builder imageBuilder = ImageMessage.newBuilder();
			imageBuilder.setImageId(key);
			imageBuilder.setImage(ByteString.copyFrom(map.get(key)));
			builder.addImages(imageBuilder.build());
		}
		
		responseBuilder.setImageResponse(builder.build());
		
	}

	@Override
	protected boolean isApplicable(MBSRequest request) {
		return request.hasImageRequest();
	}

	@Override
	protected ImageRequest extractRequestMessage(MBSRequest request) {
		return request.getImageRequest();
	}

}
