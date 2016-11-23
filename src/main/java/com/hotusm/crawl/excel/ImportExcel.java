package com.hotusm.crawl.excel;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.hotusm.crawl.excel.util.ReflectionUtil;

public class ImportExcel {
    
	/**
	 * 导入单个sheet的excel
	 * @param stream
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> importExcel(InputStream stream,Class<T> clazz) throws Exception{
			HSSFWorkbook workbook = new HSSFWorkbook(stream);
			return importExcel(clazz, workbook,0) ;
	}	

	/**
	 * 
	 * @param stream 输入流
	 * @param maps  key表示的是sheet的位置 从<strong>0</strong>开始  值是class
	 * @return 返回的map中key是位置 value是值的集合
	 * @throws Exception
	 */
	//导入多个sheet
	public  Map<Integer,List<?>> importExcel(InputStream stream,Map<Integer,Class<?>> maps) throws Exception{
		Map<Integer,List<?>> results=new HashMap<Integer, List<?>>();
		HSSFWorkbook workbook = new HSSFWorkbook(stream);
		for(Entry<Integer,Class<?>> entry:maps.entrySet()){
			results.put(entry.getKey(), importExcel(entry.getValue(),workbook, entry.getKey()));
		}
		return results;
	};
		
	private <T> T invoke(T obj,Field fld,Cell cell) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException{
		Class<?> type = fld.getType();
		if(type==short.class||type==Short.class||type==int.class||type==Integer.class||type==long.class||type==Long.class){
			ReflectionUtil.invokeSetter(fld, obj, Integer.valueOf(cell.getStringCellValue()));
		}
		else if(type==float.class||type==Float.class||type==double.class||type==Double.class){
			ReflectionUtil.invokeSetter(fld, obj, Double.valueOf(cell.getStringCellValue()));
		}
		else if(type==boolean.class||type==Boolean.class){
			ReflectionUtil.invokeSetter(fld, obj, cell.getBooleanCellValue());
		}
		else if(type==Date.class){
			ReflectionUtil.invokeSetter(fld, obj, cell.getDateCellValue());
		}
		else if(type==String.class){
			ReflectionUtil.invokeSetter(fld, obj, cell.getStringCellValue());
		}else{
			throw new IllegalArgumentException();
		}
		return obj;
	}
	

	protected <T> List<T> importExcel(Class<T> clazz,HSSFWorkbook workbook,int sheetIndex) throws Exception{
   	 
		 List<T> ts=new ArrayList<T>();
		 
		 HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
   	 //excel实体
   	 ExcelNode node = ExcelHelper.node(clazz);
   	 
   	 int rowCount = sheet.getLastRowNum();
   	 int columnCount=node.getTitles().size();
   	 T t=null;
   	 for(int i=1;i<rowCount;i++){
   		 t=clazz.newInstance();
   		 HSSFRow row = sheet.getRow(i);
   		 for(int j=0;j<columnCount;j++){
   			 invoke(t, node.getFields().get(j), row.getCell(j));
   		 }
   		 ts.add(t);
   	 }
   	 return ts;
   }
	
}
