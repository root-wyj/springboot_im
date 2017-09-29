package com.wyj.springboot.im.tools;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
	// 注册管理器
	private Registry registry = null;

	public Registry getRegistry() {
		if (null != registry) {
			return registry;
		}

		try {
			// 获取服务注册管理器
			registry = LocateRegistry.getRegistry("127.0.0.1", 8687);
			// 列出所有注册的服务
			String[] list = registry.list();
			for (String s : list) {
				System.out.println(s);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return registry;
	}

	public void test() {
		try {
			// 获取服务
			RMIService server = (RMIService) this.getRegistry().lookup("test");
			// 调用远程方法
			String result = server.find("hhh");
			System.out.println("调用结果：" + result);
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
