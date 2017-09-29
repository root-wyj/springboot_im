package com.wyj.springboot.im.tools;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIService extends Remote {
	public String find(String key) throws RemoteException;
}
