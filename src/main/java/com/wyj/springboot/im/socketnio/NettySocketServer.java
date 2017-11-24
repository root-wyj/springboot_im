package com.wyj.springboot.im.socketnio;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOServer;
import com.wyj.springboot.im.authorize.cookie.HeaderFactory;
import com.wyj.springboot.im.authorize.cookie.UserHeaderContainer;

/**
 * 
 * @author wuyingjie
 * @date 2017年11月8日
 */



//之前采用这种方式注入，然后该类里面的成员总是为null
//	我现在只想说， 这TMD 不是废话么！！！能TM有值么！！ 对象你都实例化完成了，注入到系统中了，你还让系统怎么给这个对象赋值，使用@Component完成注入
//@Bean
//public NettySocketServer socketServer() {
//	return new NettySocketServer();
//}

//我真的只想说 卧槽 卧槽  卧槽！！！
//我tm在构造方法中使用成员变量  还tm问 为什么一直为空，当然tmd为空了，对象还没初始化完呢，怎么能初始化里面的成员变量，真tmd愚啊，后来使用了@PostConstruct注解 解决了该问题 另外一个是@PreDestroy

@Component
//@PropertySource("classpath:redis.properties")
public class NettySocketServer {
	private static final Logger logger = LoggerFactory.getLogger(NettySocketServer.class);
	
	@Value("${nss.server.host}")
	private String host;
	@Value("${nss.server.port}")
	private Integer port;
	
	private SocketIOServer server;
	
	@PostConstruct
	private void init() {
		Configuration config = new Configuration();
		config.setHostname(host);
		config.setPort(port);
		System.out.println("SocketServer start at host:"+host+", port:"+port);
		
		config.setAuthorizationListener(new AuthorizationListener() {
			
			@Override
			public boolean isAuthorized(HandshakeData data) {
//				String token = data.getHttpHeaders().get(HeaderFactory.HEADER_KEY_USER_TOKEN);
				String token = data.getSingleUrlParam(HeaderFactory.HEADER_KEY_USER_TOKEN);
				UserHeaderContainer container = UserHeaderContainer.resolveUserCookie(token);
				
				if (container.getUserId() <= 0) {
					logger.info("websocket握手失败  验证用户身份失败，container:{}", container);
					return false;
				} else {
					logger.info("websocket握手成功  login!!! id:{}, uuid:{}", container.getUserId(), container.getUuid());
					return true;
				}
				
			}
		});
		
		server = new SocketIOServer(config);
//		server.addNamespace("room1");
	}
	
	public NettySocketServer() {
		
	}
	
	public SocketIOServer getServer() {
		return server;
	}
	
	/*
	 * 其实我想在onConnect中给客户端传点数据，比如说是用户信息啊 等等，所以又去翻源码，虽然还是没找到方法，但是找到了答案。
	 * 通过查看Namespace源码中发送消息的代码（比如onConnect就会向客户端发送消息），发现了这种方式：
	 * 	storeFactory.pubSubStore().publish(PubSubType.LEAVE, new JoinLeaveMessage(client.getSessionId(), getName(), getName()));
	 * 	第一个参数消息类型分为 CONNECT, DISCONNECT, JOIN, LEAVE, DISPATCH，普通消息是 DISPATCH类型。
	 * 	第二个参数是接口PubSubMessage的实现。他有下面四种实现ConnectMessage, DisConnectMessage, DispatchMessage, JoinLeaveMessage。
	 * 
	 * 通过研究Namespace的onConnect方法（就是在该方法中向客户端发送了消息，客户端收到了onConnect的监听），发现上面发送消息的方式并没有提供添加数据的接口。
	 * 	所以就去找普通发送消息的方式。在BroadcastOperations的sendEvent中找到了发送消息的源头。该方法就是用Packet对数据进行封装，最后调用了send(Packet packet)方法。
	 * 	该方法中做了两件事，1、遍历client并 client.send(packet) 2、调用dispatch方法，就是调用上面的storeFactory的方法。
	 * 
	 * client.send调用的是SocketIOClient的实现类NamespaceClient中的方法。实际上调用的是ClientHead的send方法，最后发的就是那个Packet包
	 * 
	 * 使用storeFactory发送消息，要选择消息的类型和消息的内容，消息的内容用PubSubMessage封装了，里面有的包括了消息体Packet，有的则没有消息体，如 ConnectMessage
	 * 
	 */
	
	
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
