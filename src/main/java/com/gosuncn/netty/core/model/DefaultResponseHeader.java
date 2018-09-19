package com.gosuncn.netty.core.model;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 默认响应报文头
 */
public class DefaultResponseHeader extends DefaultHeader{

	/**响应状态码*/
	private Short status;
	
	/**响应类型*/
	private Short responseType;
	
	@Override
	protected void read() {
		
		this.status = this.readShort();
		this.responseType = this.readShort();
	
	}

	@Override
	protected void write() {
		
		this.writeShort(this.status);
		this.writeShort(this.responseType);
		
	}
	

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getResponseType() {
		return responseType;
	}

	public void setResponseType(Short responseType) {
		this.responseType = responseType;
	}

	
	

}
