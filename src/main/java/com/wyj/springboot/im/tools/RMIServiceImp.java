package com.wyj.springboot.im.tools;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServiceImp extends UnicastRemoteObject implements RMIService{
	
	private static final long serialVersionUID = 1L;

	protected RMIServiceImp() throws RemoteException {
		super();
	}
	
	// 注册服务
	public static void registry() {
        Registry registry = null;
        try {
        	// 指定服务端口
            registry = LocateRegistry.createRegistry(8687);
        	RMIServiceImp server = new RMIServiceImp();
            // 绑定服务
            registry.rebind("test", server);
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }
	}
	
	// 对外提供的服务接口
	@Override
	public String find(String key) throws RemoteException {  
        System.out.println("key=" + key);  
        return "ok";  
    }
	
	
}
