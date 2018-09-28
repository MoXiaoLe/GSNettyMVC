package com.gosuncn.netty.core.model;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 默认响应报文头
 */
public class DefaultResponseHeader extends DefaultHeader{

	/**响应状态码*/
	private byte status;
	
	/**响应类型*/
	private byte responseType;
	
	@Override
	protected void read() {
		
		this.status = this.readByte();
		this.responseType = this.readByte();
	
	}

	@Override
	protected void write() {
		
		this.writeByte(this.status);
		this.writeByte(this.responseType);
		
	}
	

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getResponseType() {
		return responseType;
	}

	public void setResponseType(byte responseType) {
		this.responseType = responseType;
	}

	
	

}
