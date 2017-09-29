package com.wyj.springboot.im.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 处理Properties的常用方法
 * 
 * @Description: TODO
 * @author heyong
 * @date 2017年3月20日
 */
public abstract class PropertiesUtil {

	/**
	 * 转换Properties为Map对象
	 * 
	 * @param prop
	 * @return
	 * @author heyong
	 */
	public static Map<String, String> convertToMap(Properties prop) {
		if (prop == null) {
			return null;
		}

		Map<String, String> result = new HashMap<String, String>();
		for (Object eachKey : prop.keySet()) {
			if (eachKey == null) {
				continue;
			}

			String key = eachKey.toString();
			String value = (String) prop.get(key);
			if (value == null) {
				value = "";
			} else {
				value = value.trim();
			}

			result.put(key, value);
		}
		return result;
	}

}
