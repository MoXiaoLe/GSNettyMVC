package com.gosuncn.netty.core.model;

import java.nio.charset.Charset;

import com.gosuncn.netty.common.util.JsonUtils;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 默认解编码器对应的数据传输对象 （data transfer object）
 */
public class DefaultDTO extends Serializer{

	/**报文开始标志*/
	private int startFlag = CodecConstInface.START_FLAG;
	
	/**报文类型（区分是请求还是响应）*/
	private byte msgType;
	
	/**报文头长度*/
	private short headerLen;
	
	/**报文体长度*/
	private int bodyLen; 
	
	/**报文头*/
	private DefaultHeader header; 

	/**报文体*/
	private byte[] body;
	
	
	@Override
	protected void read() {
		
		this.startFlag = this.readInt();
		this.msgType = this.readByte();
		this.headerLen = this.readShort();
		this.bodyLen = this.readInt();
		if(this.msgType == MsgTypeInface.REQUEST){
			this.header = this.readObject(DefaultRequestHeader.class);
		}else if(this.msgType == MsgTypeInface.RESPONSE){
			this.header = this.readObject(DefaultResponseHeader.class);
		}else{
			throw new RuntimeException("不支持的报文类型");
		}
		this.body = new byte[this.bodyLen];
		this.body = this.readBytes(body);
	}

	@Override
	protected void write() {
		
		this.writeInt(this.startFlag);
		this.writeByte(this.msgType);
		this.writeShort(this.headerLen);
		this.writeInt(this.bodyLen);
		if(this.msgType == MsgTypeInface.REQUEST){
			this.writeObject(this.header, DefaultRequestHeader.class);
		}else if(this.msgType == MsgTypeInface.RESPONSE){
			this.writeObject(this.header, DefaultResponseHeader.class);
		}else{
			throw new RuntimeException("不支持的报文类型");
		}
		this.writeBytes(this.body);
		
	}
	
	public static Builder buidler(){
		return new Builder();
	}
	
	public static class Builder{
		private Byte msgType; 
		private DefaultHeader header; 
		private byte[] body;
		private StringBuilder paramsSb;
		
		public DefaultDTO build() throws NullPointerException{
			
			DefaultDTO defaultDTO = new DefaultDTO();
			if(msgType == null){
				throw new NullPointerException("消息类型（请求或响应）不能为空");
			}
			if(header == null){
				throw new NullPointerException("报文头不能为空");
			}
			defaultDTO.setMsgType(this.msgType);
			defaultDTO.setHeaderLen(this.header.getLength());
			defaultDTO.setHeader(header);
			if(body == null){
				defaultDTO.setBodyLen(0);
				defaultDTO.setBody(new byte[0]);
			}else{
				defaultDTO.setBodyLen(body.length);
				defaultDTO.setBody(body);
			}
			return defaultDTO;
		}
		
		public Builder msgType(byte msgType){
			this.msgType = msgType;
			return this;
		}
		
		public Builder header(DefaultHeader header) {
			this.header = header;
			return this;
		}
		
		public Builder body(byte[] body) {
			this.body = body;
			return this;
		}
		
		/**json方式*/
		public Builder body(Object obj){
			this.body = JsonUtils.toJsonString(obj)
					.getBytes(Charset.forName("UTF-8"));
			return this;
		}
		
		/**serializer方式*/
		public Builder body(Serializer serializer){
			this.body = serializer.getBytes();
			return this;
		}
		
		/**form表单方式*/
		public Builder keyValue(String key,String value){
			if(paramsSb == null){
				paramsSb = new StringBuilder();
			}
			paramsSb.append("&" + key + "=" + value);
			this.body = paramsSb.toString().getBytes(Charset.forName("UTF-8"));
			return this;
		}
		
	}

	public int getStartFlag() {
		return startFlag;
	}

	public void setStartFlag(int startFlag) {
		this.startFlag = startFlag;
	}
	
	public byte getMsgType() {
		return msgType;
	}

	public void setMsgType(byte msgType) {
		this.msgType = msgType;
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
