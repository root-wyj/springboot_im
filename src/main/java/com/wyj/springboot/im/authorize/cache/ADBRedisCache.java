package com.wyj.springboot.im.authorize.cache;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月20日
 */

public abstract class ADBRedisCache<V> extends RedisCacheManager<String, V>{
	
	public ADBRedisCache(long timeout) {
		super(timeout);
	}

	@Override
	public void set(String key, V value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public V get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}
	
	protected abstract V getFromDB (long id);

}
