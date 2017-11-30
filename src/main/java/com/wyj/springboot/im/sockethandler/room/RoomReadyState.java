package com.wyj.springboot.im.sockethandler.room;

import com.wyj.springboot.im.config.Constants;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.sockethandler.card.CardDesc;
import com.wyj.springboot.im.sockethandler.entity.UserInCache;
import com.wyj.springboot.im.sockethandler.entity.UserInGame;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月29日
 */

public class RoomReadyState extends ARoomState{

	@Override
	public void inRoom(UserInCache user) {
		if (!context.usersInRoom.containsKey(user.getUserId())) {
			UserInGame gameUser = new UserInGame(new User.Builder().setId(user.getUserId())
														 .setUsername(user.getUsername()).build());
			user.setRoomId(context.roomId);
			gameUser.setRoomId(context.roomId);
			context.usersInRoom.put(gameUser.getUserId(), gameUser);
			context.joinedUserId.addFirst(user.getUserId());
			RoomContext.logger.info("{} (id:{}) 进入了房间 {}", user.getUsername(), user.getUserId(), context.roomId);
		}
	}

	@Override
	public void outRoom(long userId) {
		UserInGame user = context.usersInRoom.get(userId);
		if (user != null) {
			//可能这里需要把部分数据同步到缓存里
			
			context.usersInRoom.remove(userId);
			context.joinedUserId.remove(userId);
			
			user.setRoomId(null);
			user.setRoomContext(null);
			RoomContext.logger.info("{} (id:{}) 退出了房间 {}", user.getUser().getUsername(), user.getUserId(), context.roomId);
			user = null;

		}
	}

	@Override
	public void startGame() {
		if (context.usersInRoom.size() <= 1) {
			RoomContext.logger.warn("人数太少，不能开始游戏。roomId:{}, ownerId:{}", context.roomId, context.ownerId);
			return;
		}
		
		context.winerId = 0;
		context.card = CardDesc.randomCard(context.usersInRoom.size());
		int index = 0;
		for (UserInGame u : context.usersInRoom.values()) {
			u.setCard(context.card.get(index));
			u.setThisGameCost(Constants.GAME_INIT_BASE_COST);
			u.setInGame(true);
			index ++;
		}
		context.refreshCost();
		context.preCost = Constants.GAME_INIT_BASE_COST;
		context.gameCounts++;
		RoomContext.logger.info("游戏开始!!");
		
		context.changeState(RoomContext.playingState);
	}

	@Override
	public void exitThisRound(long userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCost(long userId, int cost) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void openThisRound(long userId) {
		// TODO Auto-generated method stub
		
	}

}
