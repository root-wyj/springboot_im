package com.wyj.springboot.im.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.wyj.springboot.im.socketnio.NettySocketServer;

/**
 * 该类的run方法会在项目启动的时候执行
 * 
 * @author wuyingjie
 * @date 2017年11月8日
 */

@Component
public class SocketIOServerRunner implements CommandLineRunner{

	private final NettySocketServer socketServer;
	
	@Autowired
	public SocketIOServerRunner(NettySocketServer socketServer) {
		this.socketServer = socketServer;
	}
	
	@Override
	public void run(String... args) throws Exception {
		socketServer.getServer().start();
	}

}
