package com.gosuncn.netty.core.model;

import java.util.Map;

import com.gosuncn.netty.common.util.JsonUtils.Node;
import com.gosuncn.netty.core.common.IocContainer;

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
	/**报文头*/
	private DefaultHeader header;
	/**报文体*/
	private byte[] body;
	
	/**请求参数对象(BodyTypeEnum.SERIALIZER)*/
	private Object params;
	/**请求参数map(BodyTypeEnum.FORM)*/
	private Map<String, String[]> paramsMap;
	/**请求参数Node(BodyTypeEnum.JSON)*/
	private Node paramsNode;
	
	private GoRequest(){
		this.goContext = IocContainer.getGoContext();
	}
	
	public static GoRequest newInstance(Channel channel,DefaultHeader header,byte[] body){
		
		GoRequest goRequest = new GoRequest();
		goRequest.setChannel(channel);
		goRequest.setBody(body);
		goRequest.setHeader(header);
		goRequest.setSession(IocContainer.getSession(channel.id().asLongText()));
		
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
	public Object getParams() {
		return params;
	}
	public void setParams(Object params) {
		this.params = params;
	}
	public Node getParamsNode() {
		return paramsNode;
	}
	public void setParamsNode(Node paramsNode) {
		this.paramsNode = paramsNode;
	}
	
	

}
