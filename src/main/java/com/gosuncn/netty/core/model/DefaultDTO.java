package com.gosuncn.netty.core.model;


/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 默认解编码器对应的数据传输对象 （data transfer object）
 */
public class DefaultDTO {

	/**报文开始标志*/
	private int startFlag = CodecConst.START_FLAG;
	
	/**报文头长度*/
	private short headerLen;
	
	/**报文体长度*/
	private int bodyLen; 
	
	/**报文头*/
	private DefaultHeader header; 

	/**报文体*/
	private byte[] body;

	public int getStartFlag() {
		return startFlag;
	}

	public void setStartFlag(int startFlag) {
		this.startFlag = startFlag;
	}

	public short getHeaderLen() {
		return headerLen;
	}

	public void setHeaderLen(short headerLen) {
		this.headerLen = headerLen;
	}

	public int getBodyLen() {
		return bodyLen;
	}

	public void setBodyLen(int bodyLen) {
		this.bodyLen = bodyLen;
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
	
	
	
	

}
