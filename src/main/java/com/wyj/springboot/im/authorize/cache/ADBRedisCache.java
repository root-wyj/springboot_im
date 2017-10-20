package com.wyj.springboot.im.authorize.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月20日
 */

public abstract class ADBRedisCache<T> implements IRedisCache<T>{

	@Autowired
	RedisTemplate<String, T> redisTemplate;
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	private RedisTemplate<String, T> myTemplate;
	
	@SuppressWarnings("unchecked")
	public ADBRedisCache (Class<T> cls) {
		if (cls.isAssignableFrom(String.class)) {
			myTemplate =  (RedisTemplate<String, T>) stringRedisTemplate;
		} else {
			myTemplate = redisTemplate;
		}
	}
	
	@Override
	public void set(String key, T value) {
		myTemplate.opsForValue().set(key, value);
	}

	@Override
	public T get(String key) {
		return myTemplate.opsForValue().get(key);
	}

	@Override
	public void del(String key) {
		myTemplate.delete(key);
	}
	
	protected abstract T getFromDB (long id);

}
