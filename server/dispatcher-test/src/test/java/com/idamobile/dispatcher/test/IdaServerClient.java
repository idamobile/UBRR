package com.idamobile.dispatcher.test;

import java.io.InputStream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public class IdaServerClient {
	private String serverUrl;
	
	public IdaServerClient(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	public MBSResponse call(MBSRequest request) throws Exception {
		HttpClient client = new DefaultHttpClient();		
		HttpPost post = new HttpPost(serverUrl);				
		post.setEntity(new ByteArrayEntity(request.toByteArray()));		    	
		InputStream content = client.execute(post).getEntity().getContent();
		return MBSResponse.parseFrom(content);
	}

}
