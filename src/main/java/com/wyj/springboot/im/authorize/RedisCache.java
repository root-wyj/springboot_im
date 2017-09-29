package com.wyj.springboot.im.authorize;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.wyj.springboot.im.entity.User;

/**
 * 
 * @author wuyingjie
 * @date 2017年9月29日
 */

@Component
public class RedisCache {
	
	private static Map<String, User> map = Collections.synchronizedMap(new HashMap<String, User>());
	
	public void save(String key, User u) {
		map.put(key, u);
	}
	
	public User get(String key) {
		return map.get(key);
	}
	
	public User remove(String key) {
		return map.remove(key);
	}
	
}
