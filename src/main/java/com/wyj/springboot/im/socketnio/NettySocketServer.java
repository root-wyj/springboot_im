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
//		server.addNamespace("room1");
	}
	
	public SocketIOServer getServer() {
		return server;
	}
	
	//namespace 是什么 是客户端么， 那SocketIOServer里两个namespace，mainNamespace是用来添加监听的，另一个namespacesHub是什么意思？
	//广播发送消息的时候，getBroadcastOperations相当于new 一个BroadcastOperations，第一个参数是所有的客户端，使用mainNamespace.getAllClients获取的
	
	/*
	 * Namespace extends SocketIoNamespace 就是SocketIOServer里面mainNamespace，如果我们添加一个Namespace的话，就是添加了一个类似mainNamespace的东西，
	 * mainNamespace保存了所有客户端的信息，比如说allClients 房间信息等 还有所有的监听器
	 * 
	 * NamespaceHub 就是 SocketIOServer里面 namespaceHub，保存了该server所有的Namespace（虽然现在还不知道第二个create第二个Namespace用来干什么）和 该Server的配置
	 * 
	 * BroadcastOperations 就是一个广播器，里面保存了SocketIoClient的列表和StoreFactory（暂时认为是一个发布消息的类），所以如果想给某一类用户发消息，就可以把这类用户放入到这个类的
	 * 		实例中，然后一起发送。
	 *
	 * Namespace 里面保存房间信息，通过String类型的房间名来区别房间，Namespace通过server.getRoomOperations("room1").sendEvent 来给某房间的所有用户发送消息，
	 * 		SocketIOClient 通过方法 socketIOClient.joinRoom("room1"); 来加入房间。 房间与用户在里面通过两个Collection来绑定，可以很方便的查找房间里的所有用户
	 * 		也可以通过用户查找他在的所有房间。默认的所有用户都会进入 Namespace.DEFAULT_NAME("")，可以理解为是大厅房间。
	 * 
	 * http://www.jb51.net/article/108633.htm
	 * 
	 */
}
