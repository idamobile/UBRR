package com.idamobile.server.util.integration;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.log4j.Logger;

public class HttpUtils {

	private static String defaultEncoding = "windows-1251";

	public static void downloadFile(String url, String[] credentials, String filename)
			throws Exception {
		URL targetUrl = new URL(url);

		String scheme = targetUrl.getProtocol();
		String hostName = targetUrl.getHost();
		String uri = targetUrl.getFile();
		int port = targetUrl.getPort();
		port = port == -1 ? 80 : port;

		HttpHost host = new HttpHost(hostName, port, scheme);

		DefaultHttpClient client = new DefaultHttpClient();

		client.getCredentialsProvider().setCredentials(
				new AuthScope(host.getHostName(), host.getPort()),
				new UsernamePasswordCredentials(credentials[0], credentials[1]));

		AuthCache authCache = new BasicAuthCache();
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(host, basicAuth);

		BasicHttpContext localcontext = new BasicHttpContext();
		localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpGet httpget = new HttpGet(uri);

		HttpResponse response = client.execute(host, httpget, localcontext);
		HttpEntity entity = response.getEntity();

		
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), Charset.forName("UTF-8"));

		InputStream in = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in,
				Charset.forName(defaultEncoding)));
		String line = null;
		while ((line = reader.readLine()) != null) {
			writer.write(line);
			writer.write(Character.LINE_SEPARATOR);
		}
		in.close();

		writer.flush();
		writer.close();

	}
}
