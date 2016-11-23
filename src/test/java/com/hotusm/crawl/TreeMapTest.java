package com.hotusm.crawl;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

public class TreeMapTest extends TestCase{

	public void test_sort(){
		
		Map<Integer,String> maps=new TreeMap<Integer, String>();
		
		maps.put(1, "1");
		maps.put(4, "4");
		maps.put(2, "2");
		maps.put(100, "100");
		
		System.out.println(maps);
		
	}
	
	public void test_type(){
		
		double i=1.6D;
	}
}
