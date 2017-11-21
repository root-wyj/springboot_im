package com.wyj.springboot.im.authorize.cache.keymodel;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月21日
 */

public class UserCacheKey {
	private long userId;
	
	private String uuid;
	
	public UserCacheKey(long userId, String uuid) {
		this.userId = userId;
		this.uuid = uuid;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public String getUuid() {
		return uuid;
	}

}
