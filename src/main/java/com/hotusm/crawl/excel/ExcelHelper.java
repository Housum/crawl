package com.hotusm.crawl.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.hotusm.crawl.excel.annotation.Row;
import com.hotusm.crawl.excel.annotation.Sheet;
import com.hotusm.crawl.excel.util.ReflectionUtil;

public class ExcelHelper {
	
	//构建excel的标题
	public static <T> ExcelNode node(Class<T> clazz){
		
		Sheet sheet = clazz.getAnnotation(Sheet.class);
		if(sheet==null)
			return null;
		ExcelNode node=new ExcelNode();
		String sheetName = sheet.value();
		node.setSheetName(sheetName);
		Field[] fecLds = ReflectionUtil.getAllgetDeclaredFields(clazz);
		
		for(Field fecLd:fecLds){
			
			Row row = fecLd.getAnnotation(Row.class);
			if(row==null)continue;
			int index=row.index();
			if(index==-1){
				if(node.getTitles().size()==0){
					index=0;
				}else{
					index=node.getTitles().size();
				}
			}
			
			String columnName=row.columnName()==null?"":row.columnName();
			node.putTitle(index, columnName);
			//类型
			node.putField(index, fecLd);
		}
		
		return node;
	}
	
	//解析出实体的每一个字段
	public static <T> Map<Integer,Object> transObj2Row(T obj){
		Field[] fecLds = ReflectionUtil.getAllgetDeclaredFields(obj.getClass());
		Map<Integer,Object> rows=new TreeMap<Integer, Object>();
		for(Field fecLd:fecLds){
			Row row = fecLd.getAnnotation(Row.class);
			if(row==null){
				continue;
			}
			int index=row.index();
			if(index==-1){
				if(rows.size()==0){
					index=0;
				}else{
					index=rows.keySet().size();
				}
			}
			
			try {
				rows.put(index, ReflectionUtil.invokeGetter(fecLd, obj));
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return rows;
	}
	
	//解析一系列的
	public static <T> List<Map<Integer,Object>> transObjs2Rows(Collection<T> objs){
		List<Map<Integer,Object>> rows=new ArrayList<Map<Integer,Object>>();
		
		for(Object obj:objs){
			rows.add(transObj2Row(obj));
		}
		return rows;
	}
	
}
