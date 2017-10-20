package com.wyj.springboot.im.authorize.cache;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月20日
 */

public interface KeySerilizable<K> {
	String serilizableKey(K key);
}
