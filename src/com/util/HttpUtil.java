package com.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;  
  
 
public class HttpUtil {  
    
	
	
	/**
	 * 
	 * @param url 请求url
	 * @param xmlData xml数据
	 * @param isCert 是否加载证书
	 * @return
	 * @throws Exception
	 */
	   public static String sendXMLDataByPost(String url, String xmlData,boolean isCert) throws Exception {  
 		   String mch_id=PropertiesUtil.GetValueByKey("mch_id");
 		  HttpClient client=null;
		   //是否加载证书
 		   if(!isCert) {
 			  client=HttpClients.createDefault();
 		   }else {
 			  client = initCert(mch_id);
 		   }
		     
	        HttpPost post = new HttpPost(url);  
	        post.setEntity(new StringEntity(xmlData, "UTF-8"));  
	        HttpResponse response = client.execute(post);
 	        HttpEntity entity = response.getEntity();  
	        String result = EntityUtils.toString(entity, "UTF-8");  
	        return result;  
	    }
		/**
		 * 
		 * @param url 请求url
		 * @param xmlData xml数据
		 * @param isCert 是否加载证书
		 * @return
		 * @throws Exception
		 */
		   public static String sendPost(String url, String jsonStr) throws Exception {  
			   System.out.println("参数："+jsonStr);
			   HttpClient client=HttpClients.createDefault();
		        //新建Http  post请求  
		        HttpPost httppost = new HttpPost(url);    //登录链接
		        httppost.setEntity(new StringEntity(jsonStr, Charset.forName("UTF-8")));   
		        httppost.addHeader("Content-type","application/json; charset=utf-8");
		        httppost.setHeader("Accept", "application/json");
		        //处理请求，得到响应  
		        HttpResponse response = client.execute(httppost);   

		        //打印返回的结果  
		        HttpEntity entity = response.getEntity();  
		       // Header[] map =  response.getAllHeaders();

		        StringBuilder result = new StringBuilder();  
		        if (entity != null) {  
		            InputStream instream = entity.getContent();  
		            BufferedReader br = new BufferedReader(new InputStreamReader(instream));  
		            String temp = "";  
		            while ((temp = br.readLine()) != null) {  
		                String str = new String(temp.getBytes(), "utf-8");  
		                result.append(str).append("\r\n");  
		            }  
		        }
		        return result.toString();
		 
		    }
		   
	    /**
	     * 加载证书
	     *
	     * @param mchId 商户ID
	     * @param certPath 证书位置
	     * @throws Exception
	     */
	    private static HttpClient initCert(String mchId) throws Exception {
	    	HttpClient client =null;
	        // 证书密码，默认为商户ID
	        String key = mchId;
	        // 证书的路径
	        String path = HttpUtil.class.getResource("/apiclient_cert.p12").getPath();
	        // 指定读取证书格式为PKCS12
	        KeyStore keyStore = KeyStore.getInstance("PKCS12");
	        // 读取本机存放的PKCS12证书文件
  	        FileInputStream instream = new FileInputStream(new File(path));
	        try {
	            // 指定PKCS12的密码(商户ID)
	            keyStore.load(instream, key.toCharArray());
	        } finally {
	            instream.close();
	        }
	        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, key.toCharArray()).build();
	        SSLConnectionSocketFactory sslsf =
	                new SSLConnectionSocketFactory(sslcontext, new String[] {"TLSv1"}, null,
	                        SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	        client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	        return client;
	    }
  
 
}