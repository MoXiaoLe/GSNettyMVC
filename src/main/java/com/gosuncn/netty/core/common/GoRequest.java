package com.gosuncn.netty.core.common;

import java.util.Map;

import com.gosuncn.netty.core.model.DefaultHeader;

import io.netty.channel.Channel;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 请求域对象
 */
public class GoRequest {
	
	/**会话对象*/
	private GoSession session;
	/**应用上下文*/
	private GoContext goContext;
	/**通道*/
	private Channel channel;
	/**请求参数map*/
	private Map<String, String[]> paramsMap;
	/**报文头*/
	private DefaultHeader header;
	/**报文体*/
	private byte[] body;
	
	private GoRequest(){
		// TODO 从ioc容器中获取 GoContext
	}
	
	public static GoRequest newInstance(Channel channel,DefaultHeader header,byte[] body){
		
		GoRequest goRequest = new GoRequest();
		goRequest.setChannel(channel);
		goRequest.setBody(body);
		goRequest.setHeader(header);
		//TODO  从ioc容器中获取 GoSession
		// goRequest.setSession(session);
		
		return goRequest;
	}
	
	public GoSession getSession() {
		return session;
	}
	public void setSession(GoSession session) {
		this.session = session;
	}
	public GoContext getGoContext() {
		return goContext;
	}
	public void setGoContext(GoContext goContext) {
		this.goContext = goContext;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public Map<String, String[]> getParamsMap() {
		return paramsMap;
	}
	public void setParamsMap(Map<String, String[]> paramsMap) {
		this.paramsMap = paramsMap;
	}
	public DefaultHeader getHeader() {
		return header;
	}
	public void setHeader(DefaultHeader header) {
		this.header = header;
	}
	public byte[] getBody() {
		return body;
	}
	public void setBody(byte[] body) {
		this.body = body;
	}
	
	

}
