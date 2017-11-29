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
import com.wyj.springboot.im.sockethandler.entity.UserInCache;
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
	private IRoomService roomService;
	
	@Autowired
	public SocketRoomHandler(NettySocketServer server) {
		this.server = server.getServer();
	}
	
	
	@OnEvent(value="createRoom")
	public void createRoom(SocketIOClient client, Object data, AckRequest request) {
		UserInCache userInCache = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		Room room = roomService.createRoom(new User.Builder().setId(userInCache.getUserId()).build());
		userInCache.setRoomId(room.getRoomId()+"");
		client.joinRoom(userInCache.getRoomId());
		request.sendAckData(room);
		logger.info("create room success.");
	}
	
	@OnEvent(value="inRoom")
	public void inRoom(SocketIOClient client, String roomId, AckRequest request) {
		long roomIdL = Long.valueOf(roomId);
		UserInCache userInCache = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		
		if (roomIdL == 0) {
			request.sendAckData(ResponseBean.crtFailureResult("房间号不合法"));
		}
				
		if ((roomIdL+"").equals(userInCache.getRoomId())) {
			request.sendAckData(ResponseBean.crtFailureResult("您已在该房间"));
		}
			
		if (!StringUtil.isEmpty(userInCache.getRoomId())) {
			request.sendAckData(ResponseBean.crtFailureResult("您已在其他房间，请先退出房间"));
		}
		
		userInCache.setRoomId(roomIdL+"");
		client.joinRoom(roomIdL+"");
		Room room = roomService.getRoom(roomIdL);
		request.sendAckData(room);
		server.getRoomOperations(roomIdL+"").sendEvent("onInRoom", userInCache.getUsername()+" come in room");
	}
	
	@OnEvent(value="quitRoom")
	public void quitRoom(SocketIOClient client, Object data, AckRequest request) {
		UserInCache userInCache = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		long roomId = StringUtil.toLong(userInCache.getRoomId(), 0L);
		ResponseBean bean = ResponseBean.crtSuccessBean();
		if (roomId > 0) {
			server.getRoomOperations(roomId+"").sendEvent("onQuitRoom", userInCache.getUsername()+" out of room");
			client.leaveRoom(roomId+"");
			userInCache.setRoomId("");
			
			Room room = roomService.getRoom(roomId);
			if (room.getRoomOwner() == userInCache.getUserId()) {
//				Namespace namespace = (Namespace)server.getNamespace(Namespace.DEFAULT_NAME);
//				Iterable<SocketIOClient> clients = namespace.getRoomClients(roomId+"");
//				for (SocketIOClient c : clients) {
//					UserInGame desc = SocketConnectedHandler.clientMap.get(c.getSessionId().toString());
//					client.leaveRoom(roomId+"");
//					desc.setRoomId(0);
//				}
				bean.setMessage("房主退出房间，该房间已被移除");
			}
			
		}
		request.sendAckData(bean.toString());
	}
}
