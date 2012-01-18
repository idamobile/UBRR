package com.idamobile.server.model;

import com.idamobile.protocol.ubrr.News.NewsMessage;

/**
 * Represents piece of news.
 * @author zjor
 *
 */
public class News {

	private int newsId;
	private String title;
	private String preview;
	private String url;
	private String htmlBody;
	
	private long creationDate;
	
	public News(int newsId, String title, String preview, String url, String htmlBody, long creationDate) {
		this.newsId = newsId;
		this.title = title;
		this.preview = preview;
		this.url = url;
		this.htmlBody = htmlBody;
		this.creationDate = creationDate;
	}

	public NewsMessage createMessage() {
		NewsMessage.Builder builder = NewsMessage.newBuilder()
			.setId(newsId)
			.setTitle(title)
			.setCreationDate(creationDate);
		
		if (preview != null) {
			builder.setPreview(preview);
		}
				
		if (url != null) {
			builder.setUrl(url);			
		}
		
		if (htmlBody != null) {
			builder.setHtmlBody(htmlBody);
		}
				
		return builder.build();
	}
	
	public Integer getId() {
		return newsId;
	}
}
