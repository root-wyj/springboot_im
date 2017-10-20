package com.wyj.springboot.im.authorize.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月20日
 */

public class RedisCacheManager<K, V> implements IRedisCache<K, V>{

	@Autowired
	protected RedisTemplate<String, V> redisTemplate;
	
	private KeySerilizable<K> keySerilizable = new KeySerilizable<K>() {
		public String serilizableKey(K key) {
			return key.toString();
		};
	};
	
	private long timeout = DEFAULT_TIMEOUT;
	
	public static final long DEFAULT_TIMEOUT = -1;
	
	public RedisCacheManager(long timeout){
		redisTemplate.setKeySerializer(new StringRedisSerializer());
	}
	
	public RedisCacheManager(long timeout, KeySerilizable<K> keySerilizable) {
		this(timeout);
		if (keySerilizable != null) {
			this.keySerilizable = keySerilizable;
		}
	}
	
	@Override
	public String serilizableKey(K key) {
		return keySerilizable.serilizableKey(key);
	}

	@Override
	public void set(K key, V value) {
		redisTemplate.opsForValue().set(serilizableKey(key), value, timeout, TimeUnit.SECONDS);
	}

	@Override
	public V get(K key) {
		return redisTemplate.opsForValue().get(serilizableKey(key));
	}

	@Override
	public void del(K key) {
		redisTemplate.delete(serilizableKey(key));
	}

}
