package com.wyj.springboot.im.tools;

import java.net.URI;

public class StringUtil {
	public static boolean isEmpty(String value) {
		if (value == null || value.trim().equals("") || value.trim() == "null" || value.trim().length() <= 0) {
			return true;
		}
		return false;
	}

	public static String toUTF8(String str) {
		try {
			if (str == null)
				str = "";
			else {
				str = str.trim();
				str = new String(str.getBytes("ISO-8859-1"), "utf-8");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return str;
	}

	public static Long toLong(String s) {
		return toLong(s, null);
	}

	public static Long toLong(String s, Long defaultValue) {
		if (s == null || "".equals(s.trim()))
			return defaultValue;
		try {
			return Long.parseLong(s.trim());
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	public static Integer toInteger(String s) {
		return toInteger(s, null);
	}

	public static Integer toInteger(String s, Integer defaultValue) {
		if (s == null || "".equals(s.trim()))
			return defaultValue;
		try {
			return Integer.parseInt(s.trim());
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
	
	public static Byte toByte(String s, Byte defaultValue) {
		if (s == null || "".equals(s.trim())) {
			return defaultValue;
		}
		
		try{
			return Byte.parseByte(s.trim());
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
		
	}
	
	public static Float toFloat(String s) {
		return toFloat(s, null);
	}

	public static Float toFloat(String s, Float defaultValue) {
		if (s == null || "".equals(s.trim()))
			return defaultValue;
		try {
			return Float.parseFloat(s.trim());
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
	
	public static Double toDouble(String s) {
		return toDouble(s, null);
	}

	public static Double toDouble(String s, Double defaultValue) {
		if (s == null || "".equals(s.trim()))
			return defaultValue;
		try {
			return Double.parseDouble(s.trim());
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	// 库里保存的proprait_path就是一个图片的扩展名
	public static String getUserPortrait(long user_id, String protrait) {
		if (user_id <= 0)
			return "";
		if (StringUtil.isEmpty(protrait))
			return "";
		String retprotrait = "http://" + ProductConstants.IP + ProductConstants.PORTRAIT_REQUEST + "people_" + user_id
				+ "." + protrait;
		return retprotrait;
	}
}
