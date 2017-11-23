package com.wyj.springboot.im.sockethandler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.namespace.Namespace;
import com.wyj.springboot.im.authorize.cache.RedisCacheManager;
import com.wyj.springboot.im.authorize.cache.keymodel.UserCacheKey;
import com.wyj.springboot.im.config.BeanIocConfig;
import com.wyj.springboot.im.entity.Message;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.socketnio.NettySocketServer;
import com.wyj.springboot.im.socketnio.UserDescribe;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月8日
 */

@Component
public class SocketMessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(SocketMessageHandler.class);
	
	private final SocketIOServer server;
	
	@Resource(name=BeanIocConfig.USER_CACHE)
	private RedisCacheManager<UserCacheKey, User> userCache;
	
	@Autowired
	public SocketMessageHandler(NettySocketServer socketServer) {
		this.server = socketServer.getServer();
	}
	
	
	@OnEvent(value="talk")
	public void onEvent(SocketIOClient socketIOClient, Message message, AckRequest request) {
		logger.info("test event get data:"+JSON.toJSONString(message));
		UserDescribe describe = SocketConnectedHandler.clientMap.get(socketIOClient.getSessionId().toString());
		server.getRoomOperations(message.getRoom()).sendEvent("onTalk", describe.getUsername()+":"+message.getContent());
		logger.info("all rooms:"+((Namespace)(server.getNamespace(Namespace.DEFAULT_NAME))).getRooms());
		logger.info("client all rooms:"+socketIOClient.getAllRooms());
	}

	
	@OnEvent(value="inRoom")
	public void inRoom(SocketIOClient socketIOClient, Object data) {
		UserDescribe describe = SocketConnectedHandler.clientMap.get(socketIOClient.getSessionId().toString());
		logger.info(describe.getUsername() + " inRoom room1");
		socketIOClient.joinRoom("room1");
		server.getRoomOperations("room1").sendEvent("onRoomBroadcast", describe.getUsername()+"come in room");
	}
}
	
