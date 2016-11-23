package com.hotusm.crawl.task;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.helper.StringUtil;

import com.hotusm.crawl.config.Config;
import com.hotusm.crawl.entity.Book;
import com.hotusm.crawl.excel.ExportExcel;
import com.hotusm.crawl.service.Parser;
import com.hotusm.crawl.util.HttpClientUtil;
import com.hotusm.crawl.util.ThreadMonitor;

public class RankingTask implements Runnable{
	
	//当前书籍的数量
	private static volatile AtomicInteger bookNum=new AtomicInteger(0);
	
	//总工作数  控制爬的次数
	private static volatile AtomicInteger workCount=new AtomicInteger(0);
	
	//放入总的书籍
	private static volatile RankMap<String,Book> allBooks=new RankMap<String,Book>();
	
	private static volatile boolean finsh=false;
	
	static{
		new Thread(new MonitorCrawl(allBooks,workCount)).start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			
			@Override
			public void run() {
				System.out.println("非正常退出");
				doAfterCrawl(allBooks);
			}
		});
	}
	
	private LinkedBlockingQueue<Book> books;
	
	private HttpClient client=HttpClientUtil.getHttpClient();
	
	private Parser parser=HttpClientUtil.getParser();
	
	public RankingTask(LinkedBlockingQueue<Book> books) {
		super();
		this.books = books;
	}

	public void run() {
		
		while(!ThreadMonitor.isStop){
			try {
				Book book =books.take();
				String href=book.getUrl();
				GetMethod method = HttpClientUtil.getMethod(HttpClientUtil.encodeUrlCh(href));
				int code = client.executeMethod(method);
				//成功了
				if(200==code){
					InputStream inputStream = method.getResponseBodyAsStream();
					ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
					byte[] b = new byte[1024];
					
					while(inputStream.read(b)!=-1){
						outputStream.write(b);
					}
					Book fresh=new Book();
					fresh.setUrl(href);
					String content=new String(outputStream.toByteArray(),method.getResponseCharSet());
					//评价数
					String ratingPeople = parser.getContentAsText(".rating_people > span",content);
					if(StringUtil.isBlank(ratingPeople)){
						continue;
					}
					fresh.setRoutingPerson(Integer.valueOf(ratingPeople));
					//豆瓣评分
					String ratingNum=parser.getContentAsText(".rating_num", content);
					if(StringUtil.isBlank(ratingNum)){
						continue;
					}
					fresh.setRoutingNum(Double.valueOf(ratingNum));
					//名称
					String title=parser.getContentAsText("#wrapper span", content);
					if(StringUtil.isBlank(title)){
						continue;
					}
					fresh.setTitle(title);
					
					synchronized (allBooks) {
						
						if(allBooks.contains(fresh)){
							continue;
						};
						//操作一次
						workCount.incrementAndGet();
						
						//比较算法 比较粗糙 
						if(bookNum.get()>Config.count){
							//停止
							ThreadMonitor.isStop=true;
							ThreadMonitor.isMonitor=false;
							System.out.println("抓取完毕...");
							
							doAfterCrawl(allBooks);
							
						}else{
							//new ExportExcel().export(fresh);
							if(fresh.getRoutingPerson()>1000){
								//workCount.decrementAndGet();
								bookNum.incrementAndGet();
								allBooks.put(fresh.getUrl(), fresh);
								//continue;
							}

						}
					}
				}		
			} catch (Exception e) {
				
				continue;
			}
		}
	}
	public static void doAfterCrawl(RankMap<String,Book> map){

		List<Book> books=new LinkedList<Book>();
		Book[] arrays = map.values().toArray(new Book[]{});
		
		Arrays.sort(arrays);
		
		//取前面的一百条
		for(int i=0;i<Config.count;i++){
			books.add(arrays[i]);
		}
		while(!finsh){
			new ExportExcel().export(books,Book.class,Config.filePath);
		}
		
	}
	
	//连续十次发现 放进来的比以前最小的还要小 那么就可以认为不用爬了
	@SuppressWarnings("serial")
	private static class RankMap<K,V> extends ConcurrentHashMap<K,V>{
		
	}
	
	private static class MonitorCrawl implements Runnable{

		private Map<String,Book> books;
		private AtomicInteger workCount;
		
		public MonitorCrawl(Map<String, Book> books, AtomicInteger workCount) {
			super();
			this.books = books;
			this.workCount = workCount;
		}


		public void run() {
			
			while(!ThreadMonitor.isStop){
				System.out.println(String.format("爬取总数/符合结果:[%d/%d]", this.workCount.get(),this.books.size()));
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	

}
