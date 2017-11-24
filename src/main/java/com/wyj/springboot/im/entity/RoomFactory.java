package com.wyj.springboot.im.entity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.wyj.springboot.im.service.IRoomService;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月24日
 */

public class RoomFactory {
	
	@Value("${zjh.room.playInfo}")
	private String zjhPlayInfo;
	
	@Autowired
	private IRoomService roomService;
	
	@PostConstruct
	private void init() {
		sRoomService = this.roomService;
		sZJHPlayInfo = this.zjhPlayInfo;
	}
	
	
	static IRoomService sRoomService;
	
	static String sZJHPlayInfo;

	
	
	public static Room createRoom(User u) {
		return sRoomService.createRoom(u);
	}
	
}
