package com.idamobile.server.model;

import com.idamobile.protocol.ubrr.Banners.BannerMessage;

public class Banner {
	
	private int order;
	private String title;
	private String url;
	private String text;	
	private String imageId;
	
	public Banner() {}
	
	public Banner(String title, String url, String text, int order, String imageId) {
		this.title = title;
		this.url = url;
		this.text = text;
		this.order = order;
		this.imageId = imageId;
	}
	
	public BannerMessage createMessage() {
		
		BannerMessage.Builder builder = BannerMessage.newBuilder();
		
		if (title != null) {
			builder.setTitle(title);
		}
		
		if (url != null) {
			builder.setUrl(url);
		}
		
		if (text != null) {
			builder.setText(text);
		}
		
		builder.setOrderNumber(order);
		
		if (imageId != null) {
			builder.setImageId(imageId);
		}
				
		return builder.build();
	}
}
