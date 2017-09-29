package com.wyj.springboot.im.tools;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StreamUtil {  
	  
    /** 
     * @方法功能 InputStream 转为 byte 
     * @param InputStream 
     * @return 字节数组 
     * @throws Exception 
     */  
    public static byte[] inputStream2Byte(InputStream inStream)  
            throws Exception {  
        int count = 0;  
        while (count == 0) {  
            count = inStream.available();  
        }  
        byte[] b = new byte[count];  
        inStream.read(b);  
        return b;  
    }  
  
    /** 
     * @方法功能 byte 转为 InputStream 
     * @param 字节数组 
     * @return InputStream 
     * @throws Exception 
     */  
    public static InputStream byte2InputStream(byte[] b) throws Exception {  
        InputStream is = new ByteArrayInputStream(b);  
        return is;  
    } 
}  