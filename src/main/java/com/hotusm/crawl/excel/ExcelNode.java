package com.hotusm.crawl.excel;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

public class ExcelNode {

	private  String sheetName;
	
	private final Map<Integer,String> titles =new TreeMap<Integer, String>();
	
	private final Map<Integer,Field> fields=new TreeMap<Integer, Field>();
	
	public void putTitle(int index,String columnName){
		titles.put(index, columnName);
	}
	
	public void putField(int index,Field field){
		fields.put(index, field);
	}
	public void setSheetName(String sheetName){
		this.sheetName=sheetName;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	
	public Map<Integer, String> getTitles() {
		return titles;
	}

	public Map<Integer, Field> getFields() {
		return fields;
	}

	@Override
	public String toString() {
		return "ExcelNode [sheetName=" + sheetName + ", titles=" + titles + ", fields=" + fields + "]";
	}

	
}
