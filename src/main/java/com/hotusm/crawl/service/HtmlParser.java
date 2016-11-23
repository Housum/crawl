package com.hotusm.crawl.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser implements Parser{
	
	public HtmlParser() {
		super();
	}

	private String getContent(String selectTag,String content,boolean isText,String attr) throws Exception {
		
		Document doc=Jsoup.parse(content);
		Elements els = doc.select(selectTag);
		if(els!=null){
			Element first = els.first();
			if(isText){
				return first.text();
			}
			return first.attr(attr);
		}
		return null;
	}
	
	public String getContentAsText(String selectTag,String content) throws Exception{
		return getContent(selectTag, content, true, null);
	}
	
	public String getContentAsAttr(String selectTag,String content,String attr) throws Exception{
		return getContent(selectTag, content, false, attr);
	}
}
