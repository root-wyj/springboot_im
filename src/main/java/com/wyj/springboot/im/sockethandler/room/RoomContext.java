package com.wyj.springboot.im.sockethandler.room;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wyj.springboot.im.sockethandler.entity.RoomDesc;
import com.wyj.springboot.im.sockethandler.entity.UserInCache;
import com.wyj.springboot.im.sockethandler.entity.UserInGame;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月29日
 */

public class RoomContext implements IRoomState{
	static RoomReadyState readyState = new RoomReadyState();
	static RoomPlayingState playingState = new RoomPlayingState();
	
	
	
	private ARoomState state;
	
	void changeState(ARoomState state) {
		this.state = state;
		this.state.setContext(this);
	}
	
	public IRoomState getState() {
		return state;
	}

	@Override
	public void inRoom(UserInCache user) {
		state.inRoom(user);
	}

	@Override
	public void outRoom(long userId) {
		state.outRoom(userId);
	}

	@Override
	public void startGame() {
		state.startGame();
	}

	@Override
	public void exitThisRound(long userId) {
		state.exitThisRound(userId);
	}

	@Override
	public void addCost(long userId, int cost) {
		addCost(userId, cost);
	}

	@Override
	public void openThisRound(long userId) {
		openThisRound(userId);
	}
	
	
	public RoomContext(UserInCache owner) {
		usersInRoom = new HashMap<>();
		joinedUserId = new LinkedList<>();
		
		roomId = UUID.randomUUID().toString();
		ownerId = owner.getUserId();
		
		gameCosted = 0;
		preCost = 0;
		gameCounts = 0;
		logger.info("管理员 {} (id:{}) 创建了房间 {}", owner.getUsername(), owner.getUserId(), roomId);
		
		changeState(readyState);
		inRoom(owner);
	}
	
	
	static final Logger logger = LoggerFactory.getLogger(RoomDesc.class);
	
	public static ConcurrentMap<String, RoomContext> roomMap = new ConcurrentHashMap<>(); 
	
	//在该房间的用户
	Map<Long, UserInGame> usersInRoom;

	//发言顺序
	volatile  LinkedList<Long> joinedUserId;
	
	int preCost;
	
	long ownerId;
	
	String roomId;
	
	//游戏局数
	int gameCounts;
	
	int gameCosted;
	
	List<Integer> card;
	
	long winerId;
	
	void refreshCost() {
		logger.info("\r\n当前下注情况 roomId:{}, ownerId:{}：", roomId, ownerId);
		gameCosted = 0;
		for (UserInGame u : usersInRoom.values()) {
			gameCosted += u.getThisGameCost();
			RoomContext.logger.info("用户{}下注{}", u.getUser().getUsername(), u.getThisGameCost());
		}
		RoomContext.logger.info("总下注：{}\n", gameCosted);
	}
	
	public long getOwnerId() {
		return ownerId;
	}
	
	public String getRoomId() {
		return roomId;
	}
	
	
}
