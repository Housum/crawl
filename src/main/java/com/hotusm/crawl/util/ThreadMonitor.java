package com.hotusm.crawl.util;

public class ThreadMonitor {

	/**
	 * 工作线程是否在执行
	 */
	public static volatile boolean isStop=false;
	
	/**
	 * 监控线程是否在使用
	 */
	public static volatile boolean isMonitor=true;
	
	
}
