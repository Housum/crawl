package com.hotusm.crawl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hotusm.crawl.util.HttpClientUtil;

import junit.framework.TestCase;

public class NetworkTest extends TestCase{

	private ExecutorService es = Executors.newCachedThreadPool();
	
	public void test_fetch_content() throws Exception{
			//System.out.println(fetch("https://book.douban.com/"));
			//System.out.println(URLEncoder.encode(":", "GBK"));
		//System.out.println(replaceChinese("营销", URLEncoder.encode("营销","UTF-8")));
		String href="营销";
		href="https://book.douban.com//tag//营销";
		//System.out.println(URLEncoder.encode(href, "UTF-8").replace("%2F", "/").replace("%3A", ":"));
		
		System.out.println(URLEncoder.encode("&", "UTF-8"));
	}
	public void test_detail(){
	
		GetMethod method = HttpClientUtil.getMethod(HttpClientUtil.encodeUrlCh("https://book.douban.com/subject/1148282/"));
		HttpClient client=HttpClientUtil.getHttpClient();
		try {
			int code = client.executeMethod(method);
			InputStream inputStream = method.getResponseBodyAsStream();
			ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			
			while(inputStream.read(b)!=-1){
				outputStream.write(b);
			}
			inputStream.close();
			String content=new String(outputStream.toByteArray(),method.getResponseCharSet());
			System.out.println(content);
			Document d = Jsoup.parse(content);
			//评价数
			Element els = d.select(".rating_people > span").first();
			System.out.println("评价数："+els.text());
			//豆瓣评分
			Element els1 = d.select(".rating_num").first();
			System.out.println("豆瓣评分:"+els1.text());
			//名称
			Element els2 = d.select("#wrapper span").first();
			System.out.println("名称:"+els2.text());
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	public String fetch(final String u){
		
		Future<String> submit = es.submit(new Callable<String>() {

			public String call() throws Exception {
				 HttpClient client=new HttpClient();
				 client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY); 
				 client.getParams().setParameter("http.protocol.single-cookie-header", true); 
				 GetMethod method=new GetMethod(u);
			  	 method.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;"); 
			     method.setRequestHeader("Accept-Language", "zh-cn"); 
			     method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3"); 
			     method.setRequestHeader("Accept-Charset", "utf-8"); 
			     method.setRequestHeader("Keep-Alive", "300"); 
			     method.setRequestHeader("Connection", "Keep-Alive"); 
			     method.setRequestHeader("Cache-Control", "no-cache");
			     method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
			     
				int code = client.executeMethod(method);
				ByteArrayOutputStream ba=new ByteArrayOutputStream();
				InputStream inputStream = method.getResponseBodyAsStream();
				byte[] b=new byte[1024];
				while(inputStream.read(b)!=-1){
					ba.write(b);
				}
				return new String(ba.toByteArray(),"UTF-8");
			}
		});
		try {
			return submit.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
		
	}
	public void test_parse_html() throws Exception{
		
		Document doc = Jsoup.parse(fetch("https://book.douban.com/"));
		Set<String> booksNeed=new HashSet<String>();
		Set<String> urls=new HashSet<String>();
		for(Element e:doc.select("a")){
			if(e.attr("href").contains("https://book.douban.com/subject")){
				booksNeed.add(e.attr("href"));
			}else{
				String href=e.attr("href");
				if(href.contains("douban")||href.startsWith("/")){
					if(href.startsWith("/")){
						href="https://book.douban.com/"+URLEncoder.encode(href, "UTF-8").replace("%2F", "/").replace("%3A", ":");
						urls.add(href);
					}
				}
			}
		}
		
		String[] array = urls.toArray(new String[]{});
		
		for(int i=0;i<array.length;i++){
			
			String url=array[i];
			Document d = Jsoup.parse(fetch(url));
			for(Element e:d.select("a")){
				if(e.attr("href").contains("https://book.douban.com/subject")){
					booksNeed.add(e.attr("href"));
				}else{
					String href=e.attr("href");
					if(href.contains("douban")||href.startsWith("/")){
						if(href.startsWith("/")){
							href=url.endsWith("/")?url:url+"/"+href;
						}
						urls.add(href);
					}
				}
			}
		}
//		
		for(String book:booksNeed){
			System.out.println(book);
		}
		
		System.out.println(booksNeed.size());
	}
	
	
	public void test_url(){
		try {
			listUrl("https://book.douban.com/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void listUrl(String url) throws Exception{
		Document doc = Jsoup.parse(fetch(url));
		System.out.println(fetch(url));
		System.out.println(url);
		for(Element e:doc.select("a")){
			
			String href=e.attr("href");
			System.out.println(href);
			
			if(href.contains("https://book.douban.com/subject")){
				System.out.println(e.text()+" : "+e.attr("href"));
			}else{
				if(href.contains("douban")||href.startsWith("/")){
					if(href.startsWith("/")){
						href=url+URLEncoder.encode(href, "UTF-8").replace("%2F", "/").replace("%3A", ":");
					}
					listUrl(href);
				}
			}
		}
	}
	
	 public static String replaceChinese(String source, String replacement){  
	        String reg = "[\u4e00-\u9fa5]";  
	        Pattern pat = Pattern.compile(reg);    
	        Matcher mat=pat.matcher(source);   
	        String repickStr = mat.replaceAll(replacement);  
	        return repickStr;  
	    }  
	
}
