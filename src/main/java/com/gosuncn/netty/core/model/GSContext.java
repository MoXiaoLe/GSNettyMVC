package com.gosuncn.netty.core.model;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 应用上下文对象,单例
 */
public class GSContext {
	
	/**服务IP*/
	private String ip;
	
	/**服务端口号*/
	private int port;
	
	/**应用上下文路径（ip:port）*/
	private String contextPath;
	
	private static GSContext goContext;
	
	private GSContext(String ip,int port){
		this.ip = ip;
		this.port = port;
		this.contextPath = ip + ":" + port;
	}
	
	public static GSContext newInstance(String ip,int port){
		
		if(goContext == null){
			synchronized (GSContext.class) {
				if(goContext == null){
					goContext = new GSContext(ip, port);
				}
			}
		}
		return goContext;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	

}
