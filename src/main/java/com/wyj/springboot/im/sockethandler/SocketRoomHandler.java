package com.wyj.springboot.im.sockethandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.wyj.springboot.im.entity.common.ResponseBean;
import com.wyj.springboot.im.sockethandler.entity.UserInCache;
import com.wyj.springboot.im.sockethandler.room.RoomContext;
import com.wyj.springboot.im.socketnio.NettySocketServer;
import com.wyj.springboot.im.tools.StringUtil;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月24日
 */

@Component
public class SocketRoomHandler {

	private static final Logger logger = LoggerFactory.getLogger(SocketRoomHandler.class);
	
	private SocketIOServer server;
	
	
	@Autowired
	public SocketRoomHandler(NettySocketServer server) {
		this.server = server.getServer();
	}
	
	
	@OnEvent(value="createRoom")
	public void createRoom(SocketIOClient client, Object data, AckRequest request) {
		UserInCache userInCache = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		RoomContext room = new RoomContext(userInCache);
		userInCache.setRoomId(room.getRoomId()+"");
		RoomContext.roomMap.put(room.getRoomId(), room);
		client.joinRoom(userInCache.getRoomId());
		request.sendAckData(room);
		logger.info("{} create room({}) success.", userInCache.getUserId()+userInCache.getUsername(), userInCache.getRoomId());
	}
	
	@OnEvent(value="inRoom")
	public void inRoom(SocketIOClient client, String roomId, AckRequest request) {
		UserInCache userInCache = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		
		if (StringUtil.isEmpty(roomId)) {
			request.sendAckData(ResponseBean.crtFailureResult("房间号不合法"));
			return ;
		}
		
		RoomContext room = RoomContext.roomMap.get(roomId);
		if (room == null) {
			request.sendAckData(ResponseBean.crtFailureBean("房间不存在"));
		}
		
		if ((roomId).equals(userInCache.getRoomId())) {
			request.sendAckData(ResponseBean.crtFailureResult("您已在该房间"));
			return ;
		}
			
		if (!StringUtil.isEmpty(userInCache.getRoomId())) {
			request.sendAckData(ResponseBean.crtFailureResult("您已在其他房间，请先退出房间"));
			return ;
		}
		
		room.inRoom(userInCache);
		userInCache.setRoomId(roomId);
		client.joinRoom(roomId);
		request.sendAckData(room);
		server.getRoomOperations(roomId).sendEvent("onInRoom", userInCache.getUsername()+" come in room");
		logger.info("{} in room {}", userInCache.getUserId()+userInCache.getUsername(), userInCache.getRoomId());
		logger.info("房间里人员信息：{}", JSON.toJSON(room.getUsersInRoom().values()));
	}
	
	@OnEvent(value="quitRoom")
	public void quitRoom(SocketIOClient client, Object data, AckRequest request) {
		UserInCache userInCache = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		String roomId = userInCache.getRoomId();
		ResponseBean bean = ResponseBean.crtSuccessBean();
		if (!StringUtil.isEmpty(roomId)) {
			RoomContext room = RoomContext.roomMap.get(roomId);
			room.outRoom(userInCache.getUserId());
			
			server.getRoomOperations(roomId).sendEvent("onQuitRoom", userInCache.getUsername()+" out of room");
			client.leaveRoom(roomId+"");
			userInCache.setRoomId("");
			logger.info("{} exit room {}",userInCache.getUserId()+userInCache.getUsername(), userInCache.getRoomId());
			
			
			if (room.getUsersInRoom().size() <= 0) {
				logger.info("全部人都走光，删除房间 {}", roomId);
				request.sendAckData(bean.toString());
				return ;
			}

			
			if (room.getOwnerId() == userInCache.getUserId()) {
				long nextOwner = room.getJoinedUserId().peekLast();
				room.setOwnerId(nextOwner);
				logger.info("房主退出房间，该房间已被移除，{}变为房主", nextOwner);
				bean.setMessage("房主退出房间，该房间已被移除，"+nextOwner+"变为房主");
			}
			logger.info("房间里人员信息：{}", JSON.toJSON(room.getUsersInRoom().values()));
			
		}
		request.sendAckData(bean.toString());
	}
	
}
