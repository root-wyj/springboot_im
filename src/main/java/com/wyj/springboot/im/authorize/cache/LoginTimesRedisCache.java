package com.wyj.springboot.im.authorize.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.wyj.springboot.im.entity.User;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月20日
 */

public class LoginTimesRedisCache implements IRedisCache<User, Integer>{

	@Autowired
	RedisTemplate<String, Integer> redisTemplate;
	
	@Override
	public void set(User key, Integer value) {
		redisTemplate.opsForValue().set(serilizableKey(key), value);
	}

	@Override
	public Integer get(User key) {
		return redisTemplate.opsForValue().get(serilizableKey(key));
	}
	
	public long increment(User key) {
		return redisTemplate.opsForValue().increment(serilizableKey(key), 1L);
	}

	@Override
	public void del(User key) {
		redisTemplate.delete(serilizableKey(key));
	}
	
	@Override
	public String serilizableKey(User key) {
		return key.getClass().getName()+"_"+key.getId();
	}
	
	

}
