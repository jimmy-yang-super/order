package com.jixiang.argo.lvzheng.utils;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;



/**
 * simple introduction
 *
 * <p>detailed comment</p>
 * @author chuxuebao 2015年9月19日
 * @see
 * @since 1.0
 */

public class BusinessApplyUtils {

	private final static String MAIN_URL = "http://wsdj.baic.gov.cn:8088/"; // 工商网站，主页链接
	
	private final static String sessionId = "JSESSIONID"; 
	
	public static String getSessionId() throws ClientProtocolException, IOException{
		HttpPost httppost = new HttpPost(MAIN_URL);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.execute(httppost);
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
		for(Cookie cookie : cookies){
			if(StringUtils.equalsIgnoreCase(cookie.getName(), sessionId)){
				return cookie.getName() + "=" + cookie.getValue();
			}
		}
		return "";
	}
	
	public static void getSessionId_t() throws ClientProtocolException, IOException{
		HttpPost httppost = new HttpPost(MAIN_URL);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.execute(httppost);
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
		for(Cookie cookie : cookies){
			if(StringUtils.equalsIgnoreCase(cookie.getName(), sessionId)){
				System.out.println(cookie.getName() + "=" + cookie.getValue());
			}
		}
	}
	
}
