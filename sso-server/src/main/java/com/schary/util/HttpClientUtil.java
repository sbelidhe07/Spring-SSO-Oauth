package com.schary.util;

import org.apache.http.HttpEntity;
import org.apache.http.conn.ssl.*;
import org.apache.http.ssl.SSLContextBuilder;
import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpClientUtil {

  public String generateToken() {

      System.out.println("in httpclientutil");
      String url = "https://localhost:8081/oauth/token";
      try {
          String result = sendPOST(url);
          System.out.println(result);
          return result;
      } catch (IOException e) {
          e.printStackTrace();
      }
      return null;      

  }

  private static String sendPOST(String url) throws IOException {


        try (CloseableHttpClient httpClient = createAcceptSelfSignedCertificateClient()) {

            HttpPost post = new HttpPost(url);

            post.addHeader("content-type", "application/x-www-form-urlencoded");

            // add request parameters or form parameters

            List<NameValuePair> urlParameters = new ArrayList<>();
            urlParameters.add(new BasicNameValuePair("client_id", "sampleClientId"));
            urlParameters.add(new BasicNameValuePair("client_secret", "secret"));
            urlParameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
            urlParameters.add(new BasicNameValuePair("redirect_uri", "http://localhost:8082/ui/securedPage"));

            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            CloseableHttpResponse response = httpClient.execute(post);

            String result = EntityUtils.toString(response.getEntity());

            System.out.println(result);
            return result;
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        }

    }


  private static CloseableHttpClient createAcceptSelfSignedCertificateClient()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

        // use the TrustSelfSignedStrategy to allow Self Signed Certificates
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        // we can optionally disable hostname verification. 
        // if you don't want to further weaken the security, you don't have to include this.
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
        
        // create an SSL Socket Factory to use the SSLContext with the trust self signed certificate strategy
        // and allow all hosts verifier.
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
        
        // finally create the HttpClient using HttpClient factory methods and assign the ssl socket factory
        return HttpClients
                .custom()
                .setSSLSocketFactory(connectionFactory)
                .build();
    }

}