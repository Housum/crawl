package com.hotusm.crawl.service;

public interface Parser {

	/**
	 * 获取指定的Tag的text
	 * @description <br/> 
	 * @param selectTag
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String getContentAsText(String selectTag, String content) throws Exception;

	/**
	 * 获取指定的Tag的属性
	 * @description <br/> 
	 * @param selectTag
	 * @param content
	 * @param attr
	 * @return
	 * @throws Exception
	 */
	public String getContentAsAttr(String selectTag, String content, String attr) throws Exception;
}
