package com.wyj.springboot.im.authorize.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.wyj.springboot.im.entity.User;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月20日
 */

public class UserRedisCache implements IRedisCache<String, User>{

	@Autowired
	RedisTemplate<String, User> redisTemplate;
	
	private long timeout;
	
	/**
	 * @param timeout 单位 s
	 */
	public UserRedisCache(long timeout) {
		this.timeout = timeout;
	}
	
	@Override
	public void set(String key, User value) {
		redisTemplate.opsForValue().set(serilizableKey(key), value, timeout, TimeUnit.SECONDS);
	}

	@Override
	public User get(String key) {
		return redisTemplate.opsForValue().get(serilizableKey(key));
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(serilizableKey(key));
	}
	
	@Override
	public String serilizableKey(String key) {
		return key.toString();
	}

}
