package com.wyj.springboot.im.authorize.cache;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月20日
 */

public interface IRedisCache<K, V> extends KeySerilizable<K>{
	void set(K key, V value);
	
	V get(K key);
	
	void del(K key);
}
