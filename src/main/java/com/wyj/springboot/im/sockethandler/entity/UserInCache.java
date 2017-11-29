package com.wyj.springboot.im.sockethandler.entity;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月29日
 */

public class UserInCache {
	private long userId;
	private String username;
	private String roomId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	
}
