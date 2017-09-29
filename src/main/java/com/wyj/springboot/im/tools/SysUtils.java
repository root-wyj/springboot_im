package com.wyj.springboot.im.tools;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SysUtils {
	public static String getIpAddress() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String ipAddress = inetAddress.getHostAddress();
			return ipAddress;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
