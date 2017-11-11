package com.wyj.springboot.im.authorize.cache;

import java.util.concurrent.TimeUnit;


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
	
	//Long result = super.get(key);会抛出异常
	//因为当没有值 的时候，取出来的是0 -1这种的，不是long 所以有问题
	@Override
	public Long get(K key) {
		Object result = super.get(key);
		if (result==null) {
			return 0L;
		} else {
			return Long.valueOf(result.toString());
		}
	}
	
	@Override
	public Long getAndUpdateTTL(K key) {
		Object result = super.getAndUpdateTTL(key);
		if (result==null) {
			return 0L;
		} else {
			return Long.valueOf(result.toString());
		}
	}
	
	public long increment(K key) {
		long result = redisTemplate.opsForValue().increment(serilizableKey(key), 1L);
		if (timeout > 0) {
			redisTemplate.expire(serilizableKey(key), timeout, TimeUnit.SECONDS);
		}
		return result;
	}
	
	public long increment(K key, long delta) {
		return redisTemplate.opsForValue().increment(serilizableKey(key), delta);
	}
	
}
