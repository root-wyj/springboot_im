package com.wyj.springboot.im.service;

import com.wyj.springboot.im.entity.Room;
import com.wyj.springboot.im.entity.User;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月24日
 */

public interface IRoomService {
	Room createRoom(User u);
	
	Room getRoom(long roomId);
	
}
