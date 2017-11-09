package com.wyj.springboot.im.entity;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月9日
 */

public class Message {
	private String userName;
	private String content;
	
	public Message() {}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	private String room;

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
}
