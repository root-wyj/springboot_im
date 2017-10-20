package com.wyj.springboot.im.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 
 * @author wuyingjie
 * @date 2017年10月20日
 */

public class RedisTimerCache<T extends Object> {
	
	@Autowired
	RedisTemplate<String, T> redisTemplate;
	
	public void set (String key, T t) {
		redisTemplate.opsForValue().set(key, t);
	}
	
	public T get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
//	public T remove(String key) {
//		return redisTemplate.de
//	}
}
