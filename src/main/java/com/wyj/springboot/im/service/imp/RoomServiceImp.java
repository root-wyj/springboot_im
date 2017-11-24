package com.wyj.springboot.im.service.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wyj.springboot.im.entity.Room;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.exception.ZJHRuntimeException;
import com.wyj.springboot.im.jpa.RoomRepository;
import com.wyj.springboot.im.service.IRoomService;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月24日
 */

@Service("roomService")
public class RoomServiceImp implements IRoomService{
	
	private static final Logger logger = LoggerFactory.getLogger(RoomServiceImp.class);
	
	@Value("${zjh.room.playInfo}")
	private String zjhPlayInfo;
	
	@Autowired
	RoomRepository roomRepository;
	
	//TODO 可能要加事务
	@Override
	public Room createRoom(User u) {
		if (u == null || u.getId() <= 0) {
			logger.warn("创建room失败，user 是null。 user:{}", u);
			return null;
		}
		Room room = new Room();
		room.setInfo(zjhPlayInfo);
		room.setRoomOwner(u.getId());
		room.setPlayCounts(0);
		roomRepository.saveAndFlush(room);
		
		if (room.getRoomId() > 0) {
			return room;
		} else {
			throw new ZJHRuntimeException("创建room出错， 请稍后再试");
		}
	}
	
	@Override
	public Room getRoom(long roomId) {
		return roomRepository.findOne(roomId);
	}
}
