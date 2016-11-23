package com.hotusm.crawl.excel.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {

	public static Object invokeGetter(Field fcd,Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Method method = obj.getClass().getMethod("get"+fcd.getName().substring(0, 1).toUpperCase()+fcd.getName().substring(1, fcd.getName().length()));
		return method.invoke(obj);
	}
	
	public static void invokeSetter(Field fcd,Object obj,Object args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		Method method = obj.getClass().getMethod("set"+fcd.getName().substring(0, 1).toUpperCase()+fcd.getName().substring(1, fcd.getName().length()),fcd.getType());
		method.invoke(obj, args);
	}
	
	public static Field[] getAllgetDeclaredFields(Class<?> clazz){
		
		Field[] fecLds = clazz.getDeclaredFields();
		
		for(Field fecLd:fecLds){
			if(!fecLd.isAccessible()){
				fecLd.setAccessible(true);
			};
		}
		return fecLds;
	}
}
