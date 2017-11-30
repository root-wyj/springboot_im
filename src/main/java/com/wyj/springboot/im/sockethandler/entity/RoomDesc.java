package com.wyj.springboot.im.sockethandler.entity;

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

import com.wyj.springboot.im.config.Constants;
import com.wyj.springboot.im.entity.GameResult;
import com.wyj.springboot.im.sockethandler.card.CardDesc;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月27日
 */

public class RoomDesc {
	
	private static final Logger logger = LoggerFactory.getLogger(RoomDesc.class);
	
	public static ConcurrentMap<String, RoomDesc> roomMap = new ConcurrentHashMap<>(); 
	
	//TODO 存在这里的user可能和缓存里面的user并不是一个user model
	public Map<Long, UserInGame> usersInRoom;

	//发言顺序
	public volatile  LinkedList<Long> joinedUserId;
	
	public int preCost;
	
	//发言次数，根据该数值 来确定下一个发言者
	public int sayTimes;
	
	public long ownerId;
	
	public String roomId;
	
	//游戏局数
	public int gameCounts;
	
	public int gameCosted;
	
	public boolean isPlaying;
	
	public List<Integer> card;
	
	public long winerId;
	
	
	public RoomDesc(UserInGame owner) {
		usersInRoom = new HashMap<>();
		joinedUserId = new LinkedList<>();
		
		roomId = UUID.randomUUID().toString();
		ownerId = owner.getUserId();
		
		gameCosted = 0;
		preCost = 0;
		gameCounts = 0;
		isPlaying = false;
		logger.info("房主 {} (id:{}) 创建了房间 {}", owner.getUser().getUsername(), owner.getUserId(), roomId);
		inRoom(owner);
	}
	
	public void inRoom(UserInGame user) {
		if (!usersInRoom.containsKey(user.getUserId())) {
//			user.inRoom(this);
			usersInRoom.put(user.getUserId(), user);
			joinedUserId.addFirst(user.getUserId());
			logger.info("{} (id:{}) 进入了房间 {}", user.getUser().getUsername(), user.getUserId(), roomId);
		}
	}
	
	public boolean outRoom(long userId) {
		UserInGame user = usersInRoom.get(userId);
		if (user != null) {
			logger.info("{} (id:{}) 退出了房间 {}", user.getUser().getUsername(), user.getUserId(), roomId);
			return user.outRoom();
		}
		return true;
	}
	
	public boolean startGame() {
		if (usersInRoom.size() <= 1) {
			logger.warn("人数太少，不能开始游戏。roomId:{}, ownerId:{}", roomId, ownerId);
			return false;
		}
		
		isPlaying = true;
		winerId = 0;
		card = CardDesc.randomCard(usersInRoom.size());
		int index = 0;
		for (UserInGame u : usersInRoom.values()) {
			u.startGame(card.get(index));
			index ++;
		}
		refreshCost();
		preCost = Constants.GAME_INIT_BASE_COST;
		gameCounts++;
		logger.info("游戏开始!!");
		return true;
	}
	
	public void exitThisRound(long userId) {
		
		if (userId == joinedUserId.peek()) {
			usersInRoom.get(userId).endGame(this);
			joinedUserId.pollFirst();
			logger.info("{}(id:{})退出本局游戏。",usersInRoom.get(userId).getUser().getUsername(), userId);
			if (joinedUserId.size() == 1) {
				winerId = joinedUserId.poll();
				endGame();
			}
		} else {
			logger.warn("{}(id:{})想退出本局游戏，但是还没有轮到他说话。",usersInRoom.get(userId).getUser().getUsername(), userId);
		}
	}
	
	public void addCost(long userId, int cost) {
		if (userId == joinedUserId.peek()) {
			usersInRoom.get(userId).addThisGameCost(cost);
			refreshCost();
			Long pollId = joinedUserId.pollFirst();
			joinedUserId.offerLast(pollId);
			logger.info("{}(id:{})加注{}。",usersInRoom.get(userId).getUser().getUsername(), userId, cost);
		} else {
			logger.info("{}(id:{})想加注{}， 但是还没轮到他说话。",usersInRoom.get(userId).getUser().getUsername(), userId, cost);
		}
	}
	
	public void openThisRound() {
		winerId = joinedUserId.pollFirst();
		
		while (!joinedUserId.isEmpty()) {
			long nextUserId = joinedUserId.pollFirst();
			int compareResult = CardDesc.compareCard(usersInRoom.get(winerId).getCard(),
									usersInRoom.get(nextUserId).getCard());
			if (compareResult < 0) {
				winerId = nextUserId;
			}
		}
	}
	
	public List<GameResult> getGameResult() {
		List<GameResult> list = new ArrayList<>();
		for (UserInGame u : usersInRoom.values()) {
			list.add(u.getPreResult());
		}
		return list;
	}
	
	private void endGame() {
		isPlaying = false;
		for (UserInGame u : usersInRoom.values()) {
			u.endGame(this);
		}
		
	}
	
	private void refreshCost() {
		logger.info("当前下注情况 roomId:{}, ownerId:{}：\r\n", roomId, ownerId);
		gameCosted = 0;
		for (UserInGame u : usersInRoom.values()) {
			gameCosted += u.getThisGameCost();
			logger.info("用户{}下注{}", u.getUser().getUsername(), u.getThisGameCost());
		}
		logger.info("总下注：{}\n", gameCosted);
	}
	

}
