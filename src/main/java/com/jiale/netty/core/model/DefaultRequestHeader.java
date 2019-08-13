package com.jiale.netty.core.model;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 默认请求报文头
 */
public class DefaultRequestHeader extends DefaultHeader{

	/**请求url*/
	private String url;
	
	/**请求类型*/
	private byte requestType;
	
	@Override
	protected void read() {
		
		this.url = this.readString();
		this.requestType = this.readByte();
		
	}

	@Override
	protected void write() {
		
		this.writeString(this.url);
		this.writeByte(this.requestType);
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public byte getRequestType() {
		return requestType;
	}

	public void setRequestType(byte requestType) {
		this.requestType = requestType;
	}

}
