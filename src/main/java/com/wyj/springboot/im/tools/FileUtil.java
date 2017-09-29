package com.wyj.springboot.im.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class FileUtil {    
	public static String stringToFile(String filePath) throws IOException {
		String xmlString;
		byte[] strBuffer = null;
		int len = 0;
		File file = new File(filePath);
		
		InputStream input = new FileInputStream(file);
		len = (int) file.length();
		strBuffer = new byte[len];
		input.read(strBuffer, 0, len);
		input.close();
		
		xmlString = new String(strBuffer);
		return xmlString;
	}
	
	public static boolean existsOrCreateDir(String dirPath) {
		boolean ret = false;
		try {
			File file = new File(dirPath);
			if (!file.exists()) {
				if (file.mkdirs()) {
					ret = true;
				}
			} else {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static boolean deleteFile(String filePath) {
		boolean ret = false;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				ret = file.delete();
			} else {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 删除文件目录包括目录下的所有文件。
	 * @param dir
	 * @return
	 * @author heyong
	 */
	public static boolean deleteDir(String dir) {
		boolean ret = false;
		try {
			File file = new File(dir);
			ret = FileUtil._deleteDir(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
    }
	
	private static boolean _deleteDir(File file) throws Exception {
		if (!file.exists()) {
			return true;
		}
		
        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = FileUtil._deleteDir(new File(file, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return file.delete();
    }
	
	/**
	 * 把文件转成byte数组。
	 * @param filePath
	 * @return
	 * @author heyong
	 */
	public static byte[] bytesToFile(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				baos.write(b, 0, n);
			}
			fis.close();
			baos.close();
			buffer = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}
}