package com.hotusm.crawl.entity;

import java.io.Serializable;

import com.hotusm.crawl.excel.annotation.Row;
import com.hotusm.crawl.excel.annotation.Sheet;

@Sheet("豆瓣")
public class Book implements Serializable,Comparable<Book>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//地址
	private String url;
	//
	@Row(index=2,columnName="评分",clazz=double.class)
	private double routingNum;
	@Row(index=1,columnName="评价数",clazz=int.class)
	private int routingPerson;
	@Row(index=0,columnName="标题",clazz=String.class)
	private String title;
	
	
	public Book() {
		super();
	}
	public Book(String url, double routingNum, int routingPerson, String title) {
		super();
		this.url = url;
		this.routingNum = routingNum;
		this.routingPerson = routingPerson;
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public double getRoutingNum() {
		return routingNum;
	}
	public void setRoutingNum(double routingNum) {
		this.routingNum = routingNum;
	}
	public int getRoutingPerson() {
		return routingPerson;
	}
	public void setRoutingPerson(int routingPerson) {
		this.routingPerson = routingPerson;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Book [url=" + url + ", routingNum=" + routingNum + ", routingPerson=" + routingPerson + ", title="
				+ title + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Book){
			return this.url.equals(((Book)obj).getUrl());
		}
		return false;
	}
	public int compareTo(Book o) {
		if(o==null||this.getRoutingNum()>o.getRoutingNum()){
			return -1;
		}else if(this.getRoutingNum()<o.getRoutingNum()){
			return 1;
		}
		return 0;
	}
	
}
