package com.wyj.springboot.im.sockethandler.room;


import com.wyj.springboot.im.sockethandler.card.CardDesc;
import com.wyj.springboot.im.sockethandler.entity.UserInCache;
import com.wyj.springboot.im.sockethandler.entity.UserInGame;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月29日
 */

public class RoomPlayingState extends ARoomState{

	@Override
	public void inRoom(UserInCache user) {
		
	}

	@Override
	public void outRoom(long userId) {
		
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitThisRound(long userId) {
		if (userId == context.joinedUserId.peek()) {
			userEndGame(context.usersInRoom.get(userId), false);
			
			context.joinedUserId.pollFirst();
			RoomContext.logger.info("{}(id:{})退出本局游戏。", context.usersInRoom.get(userId).getUser().getUsername(), userId);
			if (context.joinedUserId.size() == 1) {
				context.winerId = context.joinedUserId.poll();
				roomEndGame();
			}
		} else {
			RoomContext.logger.warn("{}(id:{})想退出本局游戏，但是还没有轮到他说话。",context.usersInRoom.get(userId).getUser().getUsername(), userId);
		}
		
	}
	

	@Override
	public void addCost(long userId, int cost) {
		if (userId == context.joinedUserId.peek()) {
			if (cost < context.preCost) {
				RoomContext.logger.info("{}(id:{})想加注{}。 但是小于之前{}的注码，失败！", context.usersInRoom.get(userId).getUser().getUsername(), userId, cost, context.preCost);
				return;
			}
			
			context.usersInRoom.get(userId).addThisGameCost(cost);
			RoomContext.logger.info("{}(id:{})加注{}。",context.usersInRoom.get(userId).getUser().getUsername(), userId, cost);
			context.refreshCost();
			Long pollId = context.joinedUserId.pollFirst();
			context.joinedUserId.offerLast(pollId);
		} else {
			RoomContext.logger.info("{}(id:{})想加注{}， 但是还没轮到他说话。",context.usersInRoom.get(userId).getUser().getUsername(), userId, cost);
		}
		
	}

	@Override
	public void openThisRound(long userId) {
		
		if (context.joinedUserId.peek() != userId) {
			RoomContext.logger.info("{}(id:{})想摊牌{}， 但是还没轮到他说话。",context.usersInRoom.get(userId).getUser().getUsername(), userId);
			return ;
		}
		
		context.winerId = context.joinedUserId.pollFirst();
		
		while (!context.joinedUserId.isEmpty()) {
			long nextUserId = context.joinedUserId.pollFirst();
			int compareResult = CardDesc.compareCard(context.usersInRoom.get(context.winerId).getCard(),
					context.usersInRoom.get(nextUserId).getCard());
			if (compareResult < 0) {
				context.winerId = nextUserId;
			}
		}
		
		roomEndGame();
		
	}
	
	private void userEndGame(UserInGame userInGame, boolean isSuccess) {
		if (userInGame.isInGame()) {
			if (isSuccess) {
				userInGame.getPreResult().setCost(-userInGame.getThisGameCost()+context.gameCosted);
			} else {
				userInGame.getPreResult().setCost(-userInGame.getThisGameCost());
			}
			userInGame.getPreResult().setCard(userInGame.getCard());

			userInGame.setCard(null);
			userInGame.setThisGameCost(0);
			userInGame.addPlayCounts();

			userInGame.setInGame(false);
		}
	}
	
	private void roomEndGame() {
		context.joinedUserId.clear();
		
		for (UserInGame u : context.usersInRoom.values()) {
			userEndGame(u, u.getUserId()==context.winerId);
		}
		
		context.preCost = 0;
		context.gameCosted = 0;
		context.card.clear();
		
		context.changeState(RoomContext.readyState);
		
		context.joinedUserId.addAll(context.usersInRoom.keySet());
		
		RoomContext.logger.info("本局结束, {}赢得了这场游戏!!", context.winerId);
		
		StringBuffer sb = new StringBuffer();
		
		for (UserInGame u : context.usersInRoom.values()) {
			sb.append(u.getUser().getUsername()+"("+u.getUserId()+")")
			  .append(" 使用卡牌 "+u.getPreResult().getCard())
			  .append(" 赢得 "+u.getPreResult().getCost())
			  .append("\r\n");
		}
		RoomContext.logger.info("本局结果： {}", sb.toString());

	}

}
