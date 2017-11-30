package com.wyj.springboot.im.sockethandler.room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wyj.springboot.im.entity.GameResult;
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
		state.addCost(userId, cost);
	}

	@Override
	public void openThisRound(long userId) {
		state.openThisRound(userId);
	}
	
	
	public RoomContext(UserInCache owner) {
		usersInRoom = new HashMap<>();
		joinedUserId = new LinkedList<>();
		preResult = new ArrayList<>();
		
		roomId = UUID.randomUUID().toString();
		ownerId = owner.getUserId();
		
		gameCosted = 0;
		preRoundCost = 0;
		gameCounts = 0;
		logger.info("管理员 {} (id:{}) 创建了房间 {}", owner.getUsername(), owner.getUserId(), roomId);
		
		changeState(readyState);
		inRoom(owner);
	}
	
	
	static final Logger logger = LoggerFactory.getLogger(RoomContext.class);
	
	public static ConcurrentMap<String, RoomContext> roomMap = new ConcurrentHashMap<>(); 
	
	//在该房间的用户
	Map<Long, UserInGame> usersInRoom;
	
	List<GameResult> preResult;

	//发言顺序
	volatile  LinkedList<Long> joinedUserId;
	
	int preRoundCost;
	
	long ownerId;
	
	String roomId;
	
	//游戏局数
	int gameCounts;
	
	int gameCosted;
	
	List<Integer> card;
	
	long winerId;
	
	
	
	//该方法暂时不用
	@Deprecated
	void refreshCost() {
		logger.info("\r\n当前下注情况 roomId:{}, ownerId:{}：", roomId, ownerId);
		gameCosted = 0;
		for (UserInGame u : usersInRoom.values()) {
			if (u.isInGame()) {
				gameCosted += u.getThisGameCost();
				RoomContext.logger.info("用户{}下注{}", u.getUser().getUsername(), u.getThisGameCost());
			} else {
				// 这里是负的 所以是减
				gameCosted -= u.getPreResult().getCost();
				RoomContext.logger.info("用户{}下注{}", u.getUser().getUsername(), -u.getPreResult().getCost());
			}
		}
		RoomContext.logger.info("总下注：{}\n", gameCosted);
	}
	
	public long getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getRoomId() {
		return roomId;
	}
	
	public LinkedList<Long> getJoinedUserId() {
		return joinedUserId;
	}
	
	public Map<Long, UserInGame> getUsersInRoom() {
		return usersInRoom;
	}
	
	public int getGameCosted() {
		return gameCosted;
	}
	
	public int getPreCost() {
		return preRoundCost;
	}
	
	public long getWinerId() {
		return winerId;
	}
}
