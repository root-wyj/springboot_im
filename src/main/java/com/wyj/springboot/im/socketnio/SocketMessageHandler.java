package com.wyj.springboot.im.socketnio;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月8日
 */

@Component
public class SocketMessageHandler {

	private final SocketIOServer server;
	
	private final Map<String, String> keyMap = Collections.synchronizedMap(new HashMap<>());
	
	@Autowired
	public SocketMessageHandler(NettySocketServer socketServer) {
		this.server = socketServer.getServer();
	}
	
	
	
	@OnConnect
	public void onConnect(SocketIOClient socketIOClient) {
		String token = socketIOClient.getHandshakeData().getSingleUrlParam("token");
		System.out.println(token+" connect!!");
		keyMap.put(socketIOClient.getSessionId().toString(), token);
	}
	
	@OnDisconnect
	public void onDisConnect(SocketIOClient socketIOClient) {
		String token = socketIOClient.getHandshakeData().getSingleUrlParam("token");
		System.out.println(token+" disconnect!!");
	}
	
	@OnEvent(value="test")
	public void onEvent(SocketIOClient socketIOClient, Object data, AckRequest request) {
		String token = socketIOClient.getHandshakeData().getSingleUrlParam("token");
		System.out.println("test event get data:"+data);
		server.getBroadcastOperations().sendEvent("broadcastEvent", token + "发出了一个广播");
	}
}
