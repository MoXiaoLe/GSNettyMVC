package com.gosuncn.netty.core.common;


import com.gosuncn.netty.core.model.DefaultHeader;

import io.netty.channel.Channel;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 响应域对象
 */
public class GoResponse {
	
	/**会话对象*/
	private GoSession session;
	/**应用上下文*/
	private GoContext goContext;
	/**通道*/
	private Channel channel;
	/**报文头*/
	private DefaultHeader header;
	/**报文体*/
	private byte[] body;
	
	private GoResponse(){
		this.goContext = IocContainer.getGoContext();
	}
	
	public static GoResponse newInstance(Channel channel,DefaultHeader header,byte[] body){
		
		GoResponse goResponse = new GoResponse();
		goResponse.setChannel(channel);
		goResponse.setSession(IocContainer.getSession(channel.id().asLongText()));
		goResponse.setBody(body);
		return goResponse;
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
