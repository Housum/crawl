package com.hotusm.crawl.util;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.hotusm.crawl.service.HtmlParser;
import com.hotusm.crawl.service.Parser;

public class HttpClientUtil {

	private static Parser parser=new HtmlParser();
	private static MultiThreadedHttpConnectionManager connectionManager =null;
	public static HttpClient getHttpClient(){
		if(connectionManager==null){
			HttpConnectionManagerParams params=new HttpConnectionManagerParams();
			params.setMaxTotalConnections(100);
			connectionManager=new MultiThreadedHttpConnectionManager();
			connectionManager.setParams(params);
		}
		return new HttpClient(connectionManager);
	}
	
	public static GetMethod getMethod(String uri){
		GetMethod method = new GetMethod(uri);
		method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		method.setRequestHeader("Cache-Control", "max-age=0");
		method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36");
		method.setRequestHeader("Cache-Control", "max-age=0");
		method.setRequestHeader("Host","book.douban.com");
		method.setRequestHeader("Connection","keep-alive");
		method.setRequestHeader("Cookie", "bid=9y1bUpDz-2k; gr_user_id=b4c4582f-75c7-449b-bccc-461762a143da; ll='108288'; _ga=GA1.2.177459142.1479353158; ct=y; viewed='10750155_1767741_4941558_26871656_3118650_1881032_5325731_1217156_1833287_1043008'; _pk_ref.100001.3ac3=%5B%22%22%2C%22%22%2C1479447406%2C%22https%3A%2F%2Fwww.douban.com%2F%22%5D; ap=1; __utmt=1; gr_session_id_22c937bbd8ebd703f2d8e9445f7dfd03=e003e2cb-8f2c-4a8a-b98e-8be75672ad0d; gr_cs1_e003e2cb-8f2c-4a8a-b98e-8be75672ad0d=user_id%3A0; _pk_id.100001.3ac3=bc0cbf569ff6215a.1479353158.16.1479452671.1479444186.; _pk_ses.100001.3ac3=*; __utmt_douban=1; __utma=30149280.177459142.1479353158.1479447406.1479452668.17; __utmb=30149280.2.10.1479452668; __utmc=30149280; __utmz=30149280.1479452668.17.5.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utma=81379588.893501403.1479353158.1479444186.1479447406.16; __utmb=81379588.21.10.1479447406; __utmc=81379588; __utmz=81379588.1479436562.14.5.utmcsr=douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/; _vwo_uuid_v2=FCFCADC78AA1CB7A484DFC18CB40AC1E|5e669b2d269df13d5ffc743101311287");
		method.setRequestHeader("Referer", "https://book.douban.com/");
		method.setRequestHeader("Host", "book.douban.com");
		return method;
	}
	
	public static String encodeUrlCh (String url){  
		URI uri;
		try {
			uri = new URI(url);
			return uri.toString();
		} catch (URIException e) {
			e.printStackTrace();
		}
		
		return null;
	}  
	public static Parser getParser(){
		
		return parser;
	}
}
