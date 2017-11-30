package com.wyj.springboot.im.sockethandler.entity;

import java.io.Serializable;

import com.wyj.springboot.im.entity.GameResult;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.sockethandler.room.RoomContext;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月23日
 */

public class UserInGame implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private final long userId;
	
	private final User user;
	
	
	//状态信息
	private boolean isInGame = false;
	
	private GameResult preResult;
	
	//和进入游戏之前相关的信息
	
	
	//和进入游戏之后相关的信息
	private String roomId;
	private RoomContext room;
	
	private int thisGameCost = 0;
	private Integer card;
	
	//另外还可能有一些统计信息
	private int thisLoginPlayCounts = 0;
	
	public UserInGame(User u) {
		if (u == null || u.getId() <= 0) {
			throw new RuntimeException("error parameter u");
		}
		
		user = u;
		userId = u.getId();
		isInGame = false;
		preResult = new GameResult(userId, user.getUsername(), 0, 0);
	}
	
	public int addThisGameCost(int cost) {
		thisGameCost = thisGameCost + cost;
		return thisGameCost;
	}
	
	public int addPlayCounts() {
		return this.thisLoginPlayCounts += 1;
	}
	
	/***************** get and set start ********************/
	
	public long getUserId() {
		return userId;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getRoomId() {
		return roomId;
	}
	
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	public Integer getCard() {
		return card;
	}
	
	public void setCard(Integer card) {
		this.card = card;
	}
	
	public int getThisGameCost() {
		return thisGameCost;
	}
	
	public void setThisGameCost(int thisGameCost) {
		this.thisGameCost = thisGameCost;
	}
	
	public GameResult getPreResult() {
		return preResult;
	}

	public void setPreResult(GameResult preResult) {
		this.preResult = preResult;
	}
	
	public void setRoomContext(RoomContext room) {
		this.room = room;
	}
	
	public RoomContext getRoom() {
		return room;
	}
	
	public boolean isInGame() {
		return isInGame;
	}
	
	public void setInGame(boolean isInGame) {
		this.isInGame = isInGame;
	}
	
	/**************** get and set end *************************/
	
}
