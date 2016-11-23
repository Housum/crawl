package com.hotusm.crawl.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value=ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Sheet {
	
	//单个sheet的名称
	String value();
	//sheet的位置  
	/**
	 * 因为对应多种组合 所以不能设置前后顺序 
	 */
	@Deprecated
	int index() default -1;
}
