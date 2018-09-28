package com.gosuncn.netty.core.common;

import java.util.Set;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 服务端会话域对象抽象类
 */
public abstract class GoSession {
	
	/**通道*/
	private Channel channel;
	
	/**通道ID*/
	private String channelId;
	
	/**绑定对象key,保存在channel的attribute的key*/
	private final static AttributeKey<Object> ATTACHMENT_KEY = 
			AttributeKey.valueOf("ATTACHMENT_KEY");
	
	/**创建时间*/
	private long  createTime;

	public GoSession(Channel channel) {
		super();
		this.channel = channel;
		this.channelId = channel.id().asLongText();
		this.createTime = System.currentTimeMillis();
	}
	
	protected Object getAttachment(){
		
		return this.channel.attr(ATTACHMENT_KEY).get();
	}
	
	protected void setAttachment(Object value){
		
		this.channel.attr(ATTACHMENT_KEY).set(value);
	}
	
	protected void removeAttachment(){
		
		this.channel.attr(ATTACHMENT_KEY).set(null);
	}

	public Channel getChannel() {
		return channel;
	}

	public String getChannelId() {
		return channelId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public abstract Object getAttribute(String name);
	
	public abstract Set<String> getAttributeNames();
	
	public abstract void setAttribute(String name,Object value);
	
	public abstract void removeAttribute(String name);
	
	public abstract void removeAll();
	
}
