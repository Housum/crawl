package com.hotusm.crawl.task;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hotusm.crawl.config.Config;
import com.hotusm.crawl.entity.Book;
import com.hotusm.crawl.util.HttpClientUtil;
import com.hotusm.crawl.util.ThreadMonitor;

public class ParserTask implements Runnable{

	private LinkedBlockingQueue<Book> books;
	private String url;
	private ThreadPoolExecutor workPool;
	
	private HttpClient client=HttpClientUtil.getHttpClient();
	
	public ParserTask(LinkedBlockingQueue<Book> books,String url,ThreadPoolExecutor workPool) {
		super();
		this.books = books;
		this.url=url;
		this.workPool=workPool;
	}
	
	public void run() {
		
		GetMethod method = HttpClientUtil.getMethod(HttpClientUtil.encodeUrlCh(this.url));
		
		if(ThreadMonitor.isStop){
			return;
		}
		
		try {
			int status = client.executeMethod(method);
			//成功了
			if(status==200){
				InputStream inputStream = method.getResponseBodyAsStream();
				ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
				byte[] b = new byte[1024];
				
				while(inputStream.read(b)!=-1){
					outputStream.write(b);
				}
				inputStream.close();
				String content=new String(outputStream.toByteArray(),method.getResponseCharSet());
				Document doc = Jsoup.parse(content);
				Elements elements = doc.select("a");
				for(Element element:elements){
					String href=element.attr("href");
					if(StringUtil.isBlank(href)){
						continue;
					}
					if(href.startsWith(Config.prefix)&&!href.endsWith(Config.excludeUrl)){
						Book book=new Book();
						book.setUrl(href);
						//如果满了的话 那么就堵塞
						books.put(book);
					}else if(href.endsWith(Config.needContinueParser)){
						//加入到工作中
						workPool.execute(new ParserTask(books, Config.domain+href,workPool));
					}
				}
			}else if(status == 502 || status == 504 || status == 500 || status == 403){
				//如果出现500错误 重新试
				Thread.sleep(50);
				System.out.println("code:"+status);
				workPool.execute(new ParserTask(books, url,workPool));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			method.releaseConnection();
		}
	}
	
}
