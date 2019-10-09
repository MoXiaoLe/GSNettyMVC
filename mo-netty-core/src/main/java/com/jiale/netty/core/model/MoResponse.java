package com.jiale.netty.core.model;


import com.jiale.netty.core.common.IocContainer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 响应域对象
 */
public class MoResponse {
	
	/**
	 * 会话对象
	 */
	private MoSession session;
	/**
	 * 应用上下文
	 */
	private MoContext context;
	/**
	 * 通道
	 */
	private Channel channel;
	/**
	 * 报文类型
	 */
	private byte msgType;
	/**
	 * 状态码
	 */
	private short statusCode;
	/**
	 * 报文体
	 */
	private byte[] body;
	
	private MoResponse(){
		this.context = IocContainer.getContext();
	}
	
	public static MoResponse newInstance(ChannelHandlerContext ctx, ResponseDTO msg){

		Channel channel = ctx.channel();
		String channelId = channel.id().asLongText();
		MoResponse moResponse = new MoResponse();
		moResponse.setChannel(channel);
		moResponse.setSession(IocContainer.getSession(channelId));
		if(msg != null){
			moResponse.setMsgType(msg.msgType);
			moResponse.setStatusCode(msg.statusCode);
			moResponse.setBody(msg.body);
		}
		return moResponse;
	}


	public MoSession getSession() {
		return session;
	}

	public void setSession(MoSession session) {
		this.session = session;
	}

	public MoContext getContext() {
		return context;
	}

	public void setContext(MoContext context) {
		this.context = context;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public byte getMsgType() {
		return msgType;
	}

	public void setMsgType(byte msgType) {
		this.msgType = msgType;
	}

	public short getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(short statusCode) {
		this.statusCode = statusCode;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
}
