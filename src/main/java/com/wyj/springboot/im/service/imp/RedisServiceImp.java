package com.wyj.springboot.im.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.wyj.springboot.im.service.IRedisService;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月18日
 */

@Service
public class RedisServiceImp implements IRedisService{

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	
	@Override
	public boolean set(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
		
		return true;
	}

	@Override
	public String get(String key) {
		return redisTemplate.opsForValue().get(key);
//		String result = template.execute(new RedisCallback<String>() {
//			@Override
//			public String doInRedis(RedisConnection connection) throws DataAccessException {
//				RedisSerializer<String> serializer = template.getStringSerializer();
//				byte[] value = connection.get(serializer.serialize(key));
//				connection.close();
//				return serializer.deserialize(value);
//			}
//		});
//		return result;
	}

	@Override
	public boolean expire(String key, long expire) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				boolean result = connection.expire(serializer.serialize(key), expire);
				connection.close();
				return result;
			}
		});
	}
	
	public long del(String key) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				connection.close();
				long result = connection.del(serializer.serialize(key));
				return result;
			}
		});
	} 

}
