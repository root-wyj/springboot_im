package com.wyj.springboot.im.sockethandler.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.namespace.Namespace;
import com.wyj.springboot.im.entity.common.ResponseBean;
import com.wyj.springboot.im.service.IRoomService;
import com.wyj.springboot.im.sockethandler.SocketConnectedHandler;
import com.wyj.springboot.im.sockethandler.entity.UserInCache;
import com.wyj.springboot.im.socketnio.NettySocketServer;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月27日
 */

@Component
public class GameHandler {

	@Autowired
	IRoomService roomService;
	
	private SocketIOServer server;
	
	@Autowired
	public GameHandler(NettySocketServer server) {
		this.server = server.getServer();
	}
	
	//1. init game data   2. 告诉各个玩家游戏开始，并返回相应数据
	@OnEvent("gameStart")
	public void gameStart(SocketIOClient client, AckRequest request) {
		
		UserInCache userInCache = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		RoomContext room = RoomContext.roomMap.get(userInCache.getRoomId());
		if (room.getOwnerId() != userInCache.getUserId()) {
			request.sendAckData(ResponseBean.crtFailureResult("您不是房主，不能开始游戏"));
			return;
		}
		
		List<SocketIOClient> clients = (List<SocketIOClient>)((Namespace)(server.getNamespace(Namespace.DEFAULT_NAME))).getRoomClients(room.getRoomId());
		if (clients == null) {
			request.sendAckData(ResponseBean.crtFailureResult("该房间不存在"));
			return ;
		}
		
		if (clients.size() <= 1) {
			request.sendAckData(ResponseBean.crtFailureResult("暂时无法开始游戏，人数少于2"));
			return;
		} 
		
		room.startGame();
	}
	
	@OnEvent("yazhu")
	public void yazhu(SocketIOClient client, Integer cost, AckRequest request) {
		UserInCache userInCache = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		RoomContext room = RoomContext.roomMap.get(userInCache.getRoomId());
		room.addCost(userInCache.getUserId(), cost);
		
	}
	
	@OnEvent("exitThisRound")
	public void exitThisRound(SocketIOClient client, AckRequest request) {
		UserInCache userInCache = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		RoomContext room = RoomContext.roomMap.get(userInCache.getRoomId());
		room.exitThisRound(userInCache.getUserId());
	}
	
	@OnEvent("openThisRound")
	public void openThisRound(SocketIOClient client, AckRequest request) {
		UserInCache userInCache = SocketConnectedHandler.clientMap.get(client.getSessionId().toString());
		RoomContext room = RoomContext.roomMap.get(userInCache.getRoomId());
		room.openThisRound(userInCache.getUserId());
	}
	
}
