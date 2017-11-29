package com.wyj.springboot.im.sockethandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.wyj.springboot.im.authorize.cache.RedisCacheManager;
import com.wyj.springboot.im.authorize.cache.keymodel.UserCacheKey;
import com.wyj.springboot.im.authorize.cookie.HeaderFactory;
import com.wyj.springboot.im.authorize.cookie.UserHeaderContainer;
import com.wyj.springboot.im.config.BeanIocConfig;
import com.wyj.springboot.im.entity.User;
import com.wyj.springboot.im.entity.common.ResponseBean;
import com.wyj.springboot.im.exception.ZJHRuntimeException;
import com.wyj.springboot.im.sockethandler.entity.UserInCache;
import com.wyj.springboot.im.socketnio.NettySocketServer;
import com.wyj.springboot.im.tools.StringUtil;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月23日
 */

@Component
public class SocketConnectedHandler {

	private static final Logger logger = LoggerFactory.getLogger(SocketConnectedHandler.class);
	
	private final SocketIOServer server;
	
	public static final Map<String, UserInCache> clientMap = Collections.synchronizedMap(new HashMap<>());
	
	@Resource(name=BeanIocConfig.USER_CACHE)
	private RedisCacheManager<UserCacheKey, User> userCache;
	
	@Autowired
	public SocketConnectedHandler(NettySocketServer socketServer) {
		this.server = socketServer.getServer();
	}
	
	
	@OnConnect
	public void onConnect(SocketIOClient socketIOClient) {
//		String token = socketIOClient.getHandshakeData().getHttpHeaders().get(HeaderFactory.HEADER_KEY_USER_TOKEN);
		String token = socketIOClient.getHandshakeData().getSingleUrlParam(HeaderFactory.HEADER_KEY_USER_TOKEN);
		if (StringUtil.isEmpty(token)) {
			throw new ZJHRuntimeException("");
		}
		UserHeaderContainer container = UserHeaderContainer.resolveUserCookie(token);
		if (container == null) {
			throw new ZJHRuntimeException("");
		}
		User user = userCache.get(new UserCacheKey(container.getUserId(), container.getUuid()));
		if (user == null) {
			throw new ZJHRuntimeException("");
		}
		logger.info("{} connect!!, userId:{}", user.getUsername(), user.getId());
		UserInCache describe = new UserInCache();
		describe.setUserId(user.getId());
		describe.setUsername(user.getUsername());
		clientMap.put(socketIOClient.getSessionId().toString(), describe);
		socketIOClient.sendEvent("connectedSocket", describe);
	}
	
	@OnDisconnect
	public void onDisConnect(SocketIOClient socketIOClient) {
		String sessionId = socketIOClient.getSessionId().toString();
		UserInCache describe = clientMap.get(sessionId);
		logger.info("{} disconnect!! userId:{}", describe.getUsername(), describe.getUserId());
		clientMap.remove(sessionId);
	}
	
	@OnEvent("ackTest")
	public void test(SocketIOClient client, AckRequest request) {
		//[object Object]
		request.sendAckData(ResponseBean.crtSuccessBean());
	}
	
	@OnEvent("ackTest2")
	public void test2(SocketIOClient client, AckRequest request) {
		//{"msg":"ok","code":"0"}
		request.sendAckData(ResponseBean.crtSuccessResult());
	}

}
