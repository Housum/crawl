package com.hotusm.crawl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hotusm.crawl.config.Config;
import com.hotusm.crawl.entity.Book;
import com.hotusm.crawl.excel.ExportExcel;
import com.hotusm.crawl.excel.ImportExcel;

import junit.framework.TestCase;

public class ExcelTest extends TestCase{
	
	private String filePath="testExcel.xls";
	
	@Override
	protected void setUp() throws Exception {
		
		 System.out.println(getClass().getResource("").toString());
	}
	public void test_expoprt(){
		
		List<TestExcelEntity> entitys=new ArrayList<TestExcelEntity>();

		for(int i=0;i<10;i++){
			
			TestExcelEntity entity=new TestExcelEntity();
			entity.setCreateBy(new Date());
			entity.setIndex(i);
			entity.setWeight(0.1+i);
			entity.setTitle("标题"+i);
			
			entitys.add(entity);
		}
		List<Book> books=new ArrayList<Book>();
		for(int i=0;i<10;i++){
			
			Book book=new Book();
			book.setRoutingNum(0.1+i);
			book.setRoutingPerson(1000+i);
			book.setTitle(String.format("百年孤独第[%d]卷",i));
			books.add(book);
		}
		
		Map<Class<?>,Collection<?>> maps=new HashMap<Class<?>, Collection<?>>();
		
		maps.put(TestExcelEntity.class, entitys);
		maps.put(Book.class, books);
		new ExportExcel().export(maps,filePath);
	}
	
	public void test_single_import() throws Exception{
		
		FileInputStream fileInputStream=new FileInputStream(filePath);
		
		List<Book> books = new ImportExcel().importExcel(fileInputStream, Book.class);
		
		for(Book book:books){
			System.out.println(book.getTitle());
		}
	}
	
	public void test_mutiple_import(){
		try {
			FileInputStream fileInputStream=new FileInputStream(filePath);
			Map<Integer,Class<?>> maps=new HashMap<Integer,Class<?>>();
			
			maps.put(0, TestExcelEntity.class);
			maps.put(1, Book.class);
			
			Map<Integer, List<?>> importExcel = new ImportExcel().importExcel(fileInputStream, maps);
			System.out.println(importExcel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
