package com.wyj.springboot.im.service;

/**
 * 
 * @author wuyingjie
 * @date 2017年10月18日
 */

public interface IRedisService {
	public boolean set(String key, String value);
	
	public String get(String key);
	
	public boolean expire(String key, long expire);
	
	public void del(String key);
}
