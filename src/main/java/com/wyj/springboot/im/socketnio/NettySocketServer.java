package com.wyj.springboot.im.socketnio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.wyj.springboot.im.tools.StringUtil;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月8日
 */

//@PropertySource(value={"classpath:application.properties"}, encoding="utf-8")
public class NettySocketServer {
	
//	@Value("#{nss.server.host}")
	private String host = "localhost";
//	@Value("#{nss.server.port}")
	private Integer port = 9090;
	
	private final SocketIOServer server;
	
	public NettySocketServer() {
		Configuration config = new Configuration();
		config.setHostname(host);
		config.setPort(port);
		
		config.setAuthorizationListener(new AuthorizationListener() {
			
			@Override
			public boolean isAuthorized(HandshakeData data) {
				String token = data.getSingleUrlParam("token");
				if (StringUtil.isEmpty(token)) {
					return false;
				} else {
					System.out.println(token+" login!!!");
					return true;
				}
				
			}
		});
		
		server = new SocketIOServer(config);
	}
	
	public SocketIOServer getServer() {
		return server;
	}
	
}
