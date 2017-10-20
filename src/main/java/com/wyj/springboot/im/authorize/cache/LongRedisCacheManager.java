package com.wyj.springboot.im.authorize.cache;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月20日
 */

public class LongRedisCacheManager<K> extends RedisCacheManager<K, Long>{
	
	public LongRedisCacheManager(long timeout) {
		super(timeout);
	}
	
	public LongRedisCacheManager(long timeout, KeySerilizable<K> serilizable) {
		super(timeout, serilizable);
	}
	
	@Deprecated
	@Override
	public void set(K key, Long value) {
		super.set(key, value);
	}
	
	public long increment(K key) {
		return redisTemplate.opsForValue().increment(serilizableKey(key), 1L);
	}
	
	public long increment(K key, long delta) {
		return redisTemplate.opsForValue().increment(serilizableKey(key), delta);
	}
	
}
