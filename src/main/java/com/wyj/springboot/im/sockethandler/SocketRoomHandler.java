package com.wyj.springboot.im.sockethandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.wyj.springboot.im.entity.Room;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.entity.common.ResponseBean;
import com.wyj.springboot.im.service.IRoomService;
import com.wyj.springboot.im.socketnio.NettySocketServer;
import com.wyj.springboot.im.socketnio.UserDescribe;

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
	private IRoomService roomService;
	
	@Autowired
	public SocketRoomHandler(NettySocketServer server) {
		this.server = server.getServer();
	}
	
	
	@OnEvent(value="createRoom")
	public void createRoom(SocketIOClient client, Object data, AckRequest request) {
		UserDescribe describe = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		Room room = roomService.createRoom(new User.Builder().setId(describe.getUserId()).build());
		describe.setRoomId(room.getRoomId());
		client.joinRoom(describe.getRoomId()+"");
		request.sendAckData(room);
	}
	
	@OnEvent(value="inRoom")
	public void inRoom(SocketIOClient client, String roomId, AckRequest request) {
		long roomIdL = Long.valueOf(roomId);
		UserDescribe describe = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		
		if (roomIdL == 0) {
			request.sendAckData(ResponseBean.crtFailureResult("房间号不合法"));
		}
				
		if (describe.getRoomId() == roomIdL) {
			request.sendAckData(ResponseBean.crtFailureResult("您已在该房间"));
		}
			
		if (describe.getRoomId() > 0) {
			request.sendAckData(ResponseBean.crtFailureResult("您已在其他房间，请先退出房间"));
		}
		
		describe.setRoomId(roomIdL);
		client.joinRoom(roomIdL+"");
		Room room = roomService.getRoom(roomIdL);
		request.sendAckData(room);
		server.getRoomOperations(roomIdL+"").sendEvent("onInRoom", describe.getUsername()+" come in room");
	}
	
	@OnEvent(value="quitRoom")
	public void quitRoom(SocketIOClient client, Object data, AckRequest request) {
		UserDescribe describe = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		request.sendAckData(ResponseBean.crtSuccessBean().toString());
		if (describe.getRoomId() > 0) {
			server.getRoomOperations(describe.getRoomId()+"").sendEvent("onQuitRoom", describe.getUsername()+" out of room");
			describe.setRoomId(0);
			client.leaveRoom(describe.getRoomId()+"");
		}
	}
}
