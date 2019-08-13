package com.jiale.netty.core.model;

import java.util.Map;

import com.jiale.netty.core.common.IocContainer;

import com.jiale.netty.common.util.JsonUtils;
import io.netty.channel.Channel;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 请求域对象
 */
public class GSRequest {
	
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
	
	/**请求参数对象(BodyTypeEnum.SERIALIZER)*/
	private byte[] paramsSerializerBytes;
	/**请求参数map(BodyTypeEnum.FORM)*/
	private Map<String, String[]> paramsMap;
	/**请求参数Node(BodyTypeEnum.JSON)*/
	private JsonUtils.Node paramsNode;
	private byte[] jsonStrBytes;
	
	private GSRequest(){
		this.goContext = IocContainer.getGoContext();
	}
	
	public static GSRequest newInstance(Channel channel,DefaultHeader header,byte[] body){
		
		GSRequest goRequest = new GSRequest();
		goRequest.setChannel(channel);
		goRequest.setBody(body);
		goRequest.setHeader(header);
		goRequest.setSession(IocContainer.getSession(channel.id().asLongText()));
		
		return goRequest;
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
	public byte[] getParamsSerializerBytes() {
		return paramsSerializerBytes;
	}
	public void setParamsSerializerBytes(byte[] paramsSerializerBytes) {
		this.paramsSerializerBytes = paramsSerializerBytes;
	}
	public JsonUtils.Node getParamsNode() {
		return paramsNode;
	}
	public void setParamsNode(JsonUtils.Node paramsNode) {
		this.paramsNode = paramsNode;
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

	public byte[] getJsonStrBytes() {
		return jsonStrBytes;
	}

	public void setJsonStrBytes(byte[] jsonStrBytes) {
		this.jsonStrBytes = jsonStrBytes;
	}
	

}
