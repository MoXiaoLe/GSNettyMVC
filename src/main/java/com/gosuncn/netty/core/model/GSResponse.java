package com.gosuncn.netty.core.model;


import com.gosuncn.netty.core.common.IocContainer;

import io.netty.channel.Channel;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 响应域对象
 */
public class GSResponse {
	
	/**会话对象*/
	private GSSession session;
	/**应用上下文*/
	private GSContext goContext;
	/**通道*/
	private Channel channel;
	/**报文头*/
	private DefaultHeader header;
	/**报文体*/
	private byte[] body;
	
	private GSResponse(){
		this.goContext = IocContainer.getGoContext();
	}
	
	public static GSResponse newInstance(Channel channel,DefaultHeader header,byte[] body){
		
		GSResponse goResponse = new GSResponse();
		goResponse.setChannel(channel);
		goResponse.setSession(IocContainer.getSession(channel.id().asLongText()));
		goResponse.setBody(body);
		return goResponse;
	}
	
	public GSSession getSession() {
		return session;
	}
	public void setSession(GSSession session) {
		this.session = session;
	}
	public GSContext getGoContext() {
		return goContext;
	}
	public void setGoContext(GSContext goContext) {
		this.goContext = goContext;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
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
