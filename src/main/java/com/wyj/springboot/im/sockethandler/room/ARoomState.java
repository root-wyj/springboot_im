package com.wyj.springboot.im.sockethandler.room;

import com.wyj.springboot.im.sockethandler.entity.UserInCache;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月29日
 */

public abstract class ARoomState implements IRoomState{

	protected RoomContext context;
	
	public void setContext(RoomContext context) {
		this.context = context;
	}
	
	@Override
	public abstract void inRoom(UserInCache user);

	@Override
	public abstract void outRoom(long userId);
	
	@Override
	public abstract void startGame();

	@Override
	public abstract void exitThisRound(long userId);

	@Override
	public abstract void addCost(long userId, int cost);

	@Override
	public abstract void openThisRound(long userId);

}
