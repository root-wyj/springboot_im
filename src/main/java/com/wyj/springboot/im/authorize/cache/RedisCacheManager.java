package com.wyj.springboot.im.authorize.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月20日
 */

public class RedisCacheManager<K, V> implements IRedisCache<K, V>{

	@Resource(name="redisTemplate")
	protected RedisTemplate<String, V> redisTemplate;
	
	private KeySerilizable<K> keySerilizable = new KeySerilizable<K>() {
		public String serilizableKey(K key) {
			return key.toString();
		};
	};
	
	protected long timeout = DEFAULT_TIMEOUT;
	
	public static final long DEFAULT_TIMEOUT = -1;
	
	public RedisCacheManager(long timeout){
		this.timeout = timeout;
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
		if (timeout > 0) {
			redisTemplate.opsForValue().set(serilizableKey(key), value, timeout, TimeUnit.SECONDS);
		} else {
			redisTemplate.opsForValue().set(serilizableKey(key), value);
		}
	}

	@Override
	public V get(K key) {
		return redisTemplate.opsForValue().get(serilizableKey(key));
	}
	
	public V getAndUpdateTTL(K key) {
		V value = redisTemplate.opsForValue().get(serilizableKey(key));
		if (value != null && timeout > 0) {
			redisTemplate.expire(serilizableKey(key), timeout, TimeUnit.SECONDS);
		}
		return value;
	}

	@Override
	public void del(K key) {
		redisTemplate.delete(serilizableKey(key));
	}
	
	public long getTTL(K key) {
		return redisTemplate.getExpire(serilizableKey(key), TimeUnit.SECONDS);
	}
	
	public int delAll(String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);
		for (String key : keys) {
			redisTemplate.delete(key);
		}
		return keys.size();
	}

}
