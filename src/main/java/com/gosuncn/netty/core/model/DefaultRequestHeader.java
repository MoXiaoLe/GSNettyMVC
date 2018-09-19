package com.gosuncn.netty.core.model;

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
	private Short requestType;
	
	@Override
	protected void read() {
		
		this.url = this.readString();
		this.requestType = this.readShort();
		
	}

	@Override
	protected void write() {
		
		this.writeString(this.url);
		this.writeShort(this.requestType);
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Short getRequestType() {
		return requestType;
	}

	public void setRequestType(Short requestType) {
		this.requestType = requestType;
	}

}
