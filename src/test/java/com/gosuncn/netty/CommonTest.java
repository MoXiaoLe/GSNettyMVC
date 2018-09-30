package com.gosuncn.netty;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class CommonTest {

	
	@Test
	public void getLocalIP() throws UnknownHostException{
		
		  InetAddress addr = InetAddress.getLocalHost();  
	      String ip=addr.getHostAddress().toString(); //获取本机ip  
	      String hostName=addr.getHostName().toString(); //获取本机计算机名称  
	      System.out.println(ip);
	      System.out.println(hostName);
	      
	      
		
	}
	
	@Test 
	public void testRegx(){
		
		String url = "http:////tool.oschina.net///regex///".replaceAll("/+", "/");
		
		System.out.println(url);
	}
	
	
}
