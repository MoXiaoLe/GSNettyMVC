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
	
	private Double aa;
	
	
	public Double getAa() {
		return aa;
	}

	public void setAa(Double aa) {
		this.aa = aa;
	}

	
	
	
	@Override
	protected void read() {
		
		this.url = this.readString();
		this.aa = this.readDouble();
		this.requestType = this.readShort();
		
	}

	@Override
	protected void write() {
		
		this.writeString(this.url);
		this.writeDouble(this.aa);
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
