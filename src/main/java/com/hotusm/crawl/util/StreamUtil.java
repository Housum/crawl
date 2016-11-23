package com.hotusm.crawl.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {
	
	public static String byte2String(InputStream stream,String charset) throws IOException{
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		
		while(stream.read(b)!=-1){
			outputStream.write(b);
		}
		return new String(outputStream.toByteArray(),charset);
	}
}
