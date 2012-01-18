package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	
	public static String get(String requestUrl) {
		StringBuilder data = new StringBuilder();
		
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connx = (HttpURLConnection)url.openConnection();
			connx.setRequestMethod("GET");
			connx.setDoInput(true);
			connx.setDoOutput(true);
			
			BufferedReader bReader = new BufferedReader(new InputStreamReader(connx.getInputStream()));
			String line = null;
			
			while ((line = bReader.readLine()) != null) {
				data.append(line);
			}
			
			bReader.close();
						
		} catch (Exception e) {			
			e.printStackTrace();
			data.append(e.getMessage());
		}
				
		return data.toString();
	}
}
