package com.gosuncn.netty.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 获取网络信息工具类
 */
public class INetUtils {
	
	public static String getLocalIP(){
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostAddress().toString(); 
		} catch (UnknownHostException e) {
			return "127.0.0.1";
		}  
	}
	
	
	
}
