package com.jiale.netty.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 获取网络信息工具类
 */
public class INetUtils {
	
	public static final String REGEX_IP = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
	
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
