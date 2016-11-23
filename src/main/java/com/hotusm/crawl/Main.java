package com.hotusm.crawl;

import com.hotusm.crawl.config.Config;
import com.hotusm.crawl.service.DoubanServer;
import com.hotusm.crawl.service.DoubanServerImpl;

public class Main {

	public static void main(String[] args) {
		
		
		DoubanServer doubanServer=new DoubanServerImpl();
		
		doubanServer.startCrawl(Config.keyWords);
	}
}
