package com.wyj.springboot.im.authorize.cache;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
	
	private long timeout = DEFAULT_TIMEOUT;
	
	public static final long DEFAULT_TIMEOUT = -1;
	
	@PostConstruct
	private void init() {
		redisTemplate.setKeySerializer(new StringRedisSerializer());
	}
	
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

}
