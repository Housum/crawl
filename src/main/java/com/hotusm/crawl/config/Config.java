package com.hotusm.crawl.config;

public class Config {

	//关键字
	public static String keyWords="互联网,编程,算法";

	//主域名
	public static String domain="https://book.douban.com/";
	
	//满足条件的前缀  这个可以是任何的
	public static String prefix="https://book.douban.com/subject/";
	
	//查询的主路径
	public static String searchUrl="https://book.douban.com/tag/";
	
	//排除的url后缀
	public static String excludeUrl="buylinks";
	
	//解析的线程数   最好通过计算设置为可伸缩性的
	public static int pserseThreadCount=20;
	//排名的线程数 最好通过计算设置为可伸缩性的
	public static int rankingThreadCount=20;
	//需要继续解析的url后缀
	public static String needContinueParser="type=S";
	//获取数据的数量
	public static int count=60;
	//是否要伸缩性
	public static boolean isScale=true;
	
	public static String filePath="C://DoubanBookcrawl.xls";
	
	//后期增加 伸缩性的线程数
	static{

		if(isScale){
			
			int NCPU=Runtime.getRuntime().availableProcessors();
			//80%的工作
			
			
		}
	}
	
	
	
}
