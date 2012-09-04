package com.idamobile.dispatcher.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
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
import com.idamobile.protocol.ubrr.Commons.Platform;
import com.idamobile.protocol.ubrr.Commons.Resolution;
import com.idamobile.protocol.ubrr.Protocol.ImageRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSRequest;
import com.idamobile.protocol.ubrr.Protocol.MBSResponse;

public class App {
//	public static final String IDA_SERVER_URL = "http://project.idamob.ru:8000/idaserver-UBRR/request/";
//	public static final String IDA_SERVER_URL = "http://91.208.121.19:8080/idaserver/request/";
    public static final String IDA_SERVER_URL = "http://localhost:8000/idaserver-UBRR/request/";
//	public static final String IDA_SERVER_URL  = "http://localhost:8000/idaserver/request/";

    /**
     * @param args
     * @throws Exception
     */
    public static void main( String[] args ) throws Exception {
        HttpClient client = getTrustAllClient(new DefaultHttpClient());

        HttpPost post = new HttpPost(IDA_SERVER_URL);

        MBSRequest.Builder request = MBSRequest.newBuilder();

        request.setBannersRequest(BannersRequest.newBuilder().setLastUpdateTime(0L));
        request.setImageRequest(ImageRequest.newBuilder().addImageIds("1346765097600").setPlatform(Platform.iPad).setResolution(Resolution.HDPI));


        post.setEntity(new ByteArrayEntity(request.build().toByteArray()));

        long start = System.currentTimeMillis();
        HttpResponse resp = client.execute(post);
        long end = System.currentTimeMillis();
        System.out.println(end - start + " ms");
        InputStream content = resp.getEntity().getContent();

        int statusCode = resp.getStatusLine().getStatusCode();
        System.out.println("Response Code: " + statusCode);
        Header[] headers = resp.getHeaders("RequestHandlingTime");
        System.out.println(Arrays.asList(headers));

        if (statusCode == 200) {
            System.out.println(MBSResponse.parseFrom(content));
        } else {
            System.out.println(convertStreamToString(content));
        }


    }

    public static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    @SuppressWarnings("deprecation")
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
