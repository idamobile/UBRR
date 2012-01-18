package com.idamobile.dispatcher.test;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.idamobile.dispatcher.ssl.TrustAllManager;
import com.idamobile.protocol.ubrr.Banners.BannersRequest;
import com.idamobile.protocol.ubrr.Commons.GeoPointMessage;
import com.idamobile.protocol.ubrr.Currency.CurrencyRateRequest;
import com.idamobile.protocol.ubrr.Locations.AtmsRequest;
import com.idamobile.protocol.ubrr.Locations.OfficesRequest;
import com.idamobile.protocol.ubrr.News.NewsRequest;
import com.idamobile.protocol.ubrr.Partners.MapPartnersRequest;
import com.idamobile.protocol.ubrr.Partners.NearestPartnerRequest;
import com.idamobile.protocol.ubrr.Partners.PartnersRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public class App {
	public static final String IDA_SERVER_URL = "http://project.idamob.ru:8000/idaserver-UBRR/request/";
	//public static final String IDA_SERVER_URL = "http://localhost:8000/idaserver-UBRR/request/";
	 
    /**
     * @param args
     * @throws Exception
     */
    public static void main( String[] args ) throws Exception {
    	HttpClient client = getTrustAllClient(new DefaultHttpClient());
    	
    	HttpPost post = new HttpPost(IDA_SERVER_URL);
    	
    	MBSRequest.Builder request = MBSRequest.newBuilder();    	

    	//request.setNewsRequest(NewsRequest.newBuilder().addNewsIds(1000).addNewsIds(2));
    	//request.setCurrencyRequest(CurrencyRateRequest.newBuilder());
    	
    	//request.setAtmsRequest(AtmsRequest.newBuilder().setLastUpdateTime(0l));
    	//request.setOfficesRequest(OfficesRequest.newBuilder().setLastUpdateTime(0l));
    	
    	request.setMapPartnersRequest(MapPartnersRequest.newBuilder()
    			.setTopLeft(GeoPointMessage.newBuilder().setLatitude(50.0).setLongitude(30.1))
    			.setBottomRight(GeoPointMessage.newBuilder().setLatitude(56.6).setLongitude(40.5)));
    	
    	
    	post.setEntity(new ByteArrayEntity(request.build().toByteArray()));
    	    	
    	InputStream content = client.execute(post).getEntity().getContent();
    	System.out.println(MBSResponse.parseFrom(content));
    	
    }

    private static HttpClient getTrustAllClient(HttpClient base) throws Exception {
    	SSLContext ctx = SSLContext.getInstance("TLS");
    	ctx.init(null, new TrustManager[]{new TrustAllManager()}, null);
    	SSLSocketFactory ssf = new SSLSocketFactory(ctx);
    	ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);    	
    	
    	ClientConnectionManager ccm = base.getConnectionManager();
    	SchemeRegistry sr = ccm.getSchemeRegistry();
    	sr.register(new Scheme("https", ssf, 443));
    	
    	return new DefaultHttpClient(ccm, base.getParams());
    }
    
}
