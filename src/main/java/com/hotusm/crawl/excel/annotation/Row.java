package com.hotusm.crawl.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value=ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Row {

	//列序号
	public int index() default -1;
	//列明
	public String columnName() default "";
	//字段的类型
	public Class<?> clazz() default Object.class;
	
}
