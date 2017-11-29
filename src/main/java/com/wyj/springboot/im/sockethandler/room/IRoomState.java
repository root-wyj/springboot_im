package com.wyj.springboot.im.sockethandler.room;

import com.wyj.springboot.im.sockethandler.entity.UserInCache;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月29日
 */

public interface IRoomState {
	
	void inRoom(UserInCache user);

	void outRoom(long userId);
	
	void startGame();
	
	void exitThisRound(long userId);
	
	void addCost(long userId, int cost);
	
	void openThisRound(long userId);
}
