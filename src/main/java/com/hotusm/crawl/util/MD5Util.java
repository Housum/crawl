package com.hotusm.crawl.util;

import java.security.MessageDigest;

public class MD5Util {

	public static byte[] encryptMD5(byte[] data) throws Exception {  
		  
	    MessageDigest md5 = MessageDigest.getInstance("MD5");  
	    md5.update(data);
	    return md5.digest();  
	}
	
	public static byte[] encryptMD5(String data,String charset) throws Exception{
		
		return encryptMD5(data.getBytes(charset));
	}
}
