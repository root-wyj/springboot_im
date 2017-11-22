package com.wyj.springboot.im.socketnio;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.namespace.Namespace;
import com.wyj.springboot.im.entity.Message;

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
		printNamespaces();
	}
	
	@OnDisconnect
	public void onDisConnect(SocketIOClient socketIOClient) {
		String token = socketIOClient.getHandshakeData().getSingleUrlParam("token");
		System.out.println(token+" disconnect!!");
		printNamespaces();
	}
	
	@OnEvent(value="talk")
	public void onEvent(SocketIOClient socketIOClient, Message message, AckRequest request) {
		String token = socketIOClient.getHandshakeData().getSingleUrlParam("token");
		System.out.println("test event get data:"+JSON.toJSONString(message));
		server.getRoomOperations(message.getRoom()).sendEvent("onTalk", token+":"+message.getContent());
	}
	
	@OnEvent(value="inRoom")
	public void inRoom(SocketIOClient socketIOClient, Object data) {
		String token = socketIOClient.getHandshakeData().getSingleUrlParam("token");
		System.out.println(token + " inRoom room1");
		socketIOClient.joinRoom("room1");
		server.getRoomOperations("room1").sendEvent("onRoomBroadcast", token+"come in room");
	}
	
	private void printNamespaces() {
		Collection<SocketIOClient> clients = server.getAllClients();
		System.out.println(clients);
		Collection<SocketIONamespace> namespaces = server.getAllNamespaces();
		System.out.println(namespaces);
		System.out.println(((Namespace)server.getNamespace(Namespace.DEFAULT_NAME)).getRooms());
		System.out.println(((Namespace)server.getNamespace(Namespace.DEFAULT_NAME)).getRoomClients("room1"));
//		server.getNamespace("hehe");
		
	}
}
