package com.gosuncn.netty.core.model;

import org.springframework.util.StringUtils;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 默认报文头
 */
public abstract class DefaultHeader extends Serializer{
	
	public short getLength() {
		return (short)getBytes().length;
	}
	
	public static RequestHeaderBuilder requestHeaderBuilder(){
		
		return new RequestHeaderBuilder();
	}
	
	public static ResponseHeaderBuilder responseHeaderBuilder(){
		
		return new ResponseHeaderBuilder();
	}
	
	

	public static class RequestHeaderBuilder{
		private String url;
		private byte requestType = BodyTypeEnum.JSON.getValue();
		
		public RequestHeaderBuilder url(String url){
			
			this.url = url;
			return this;
		}
		
		public RequestHeaderBuilder requestType(byte requestType){
			
			this.requestType = requestType;
			return this;
		}
		
		public DefaultHeader build() throws NullPointerException{
			
			if(StringUtils.isEmpty(this.url)){
				throw new NullPointerException("请求url不能为空");
			}
			DefaultRequestHeader header = new DefaultRequestHeader();
			header.setUrl(this.url);
			header.setRequestType(this.requestType);
			return header;
		}
		
	}
	
	public static class ResponseHeaderBuilder{
		private byte status = 0;
		private byte responseType = BodyTypeEnum.JSON.getValue();
		
		public ResponseHeaderBuilder status(byte status){
			
			this.status = status;
			return this;
		}
		
		public ResponseHeaderBuilder responseType(byte responseType){
			
			this.responseType = responseType;
			return this;
		}
		
		public DefaultHeader build(){
			
			DefaultResponseHeader header = new DefaultResponseHeader();
			header.setStatus(this.status);
			header.setResponseType(this.responseType);
			return header;
		}
		
	}
	
	
}
