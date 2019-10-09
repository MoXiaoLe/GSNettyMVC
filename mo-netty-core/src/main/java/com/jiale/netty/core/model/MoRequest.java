package com.jiale.netty.core.model;

import com.jiale.netty.core.common.IocContainer;
import com.jiale.netty.core.util.ParseUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 请求域对象
 */
public class MoRequest {
	
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
	 * 消息类型：1-键值对 2-SERIALIZER
	 */
	private byte msgType;
	/**
	 * 请求url
	 */
	private byte[] url;
	/**
	 * 报文体
	 */
	private byte[] body;
	/**
	 * 参数map(SystemConst.FORM)
	 */
	private Map<String, String[]> paramsMap;

	
	private MoRequest(){
		this.context = IocContainer.getContext();
	}
	
	public static MoRequest newInstance(ChannelHandlerContext ctx, RequestDTO msg){

		Channel channel = ctx.channel();
		String channelId = channel.id().asLongText();
		MoRequest moRequest = new MoRequest();
		moRequest.setChannel(channel);
		moRequest.setSession(IocContainer.getSession(channelId));
		if(msg != null){
			moRequest.setMsgType(msg.msgType);
			moRequest.setBody(msg.body);
			moRequest.setUrl(msg.url);
			if(msg.msgType == SystemConst.FORM){
				moRequest.setParamsMap(ParseUtils.parseFormMap(msg.body));
			}
		}
		return moRequest;
	}

	public String[] getParameterNames(){
		if(paramsMap != null){
			String[] strs = new String[paramsMap.keySet().size()];
			return paramsMap.keySet().toArray(strs);
		}
		return new String[0];
	}
	
	public String getParameter(String name){
		String[] strs = null;
		if(paramsMap != null){
			strs = paramsMap.get(name);
		}
		if(strs != null && strs.length > 0){
			return strs[0];
		}
		return null;
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

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public Map<String, String[]> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, String[]> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public byte getMsgType() {
		return msgType;
	}

	public void setMsgType(byte msgType) {
		this.msgType = msgType;
	}

	public byte[] getUrl() {
		return url;
	}

	public void setUrl(byte[] url) {
		this.url = url;
	}
}
