package com.gosuncn.netty.core.model;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月21日
 * @description 报文类型枚举
 */
public enum MsgTypeEnum {
	
	/**请求*/
	REQUEST((byte)0),
	
	/**响应*/
	RESPONSE((byte)1);
	
	private byte value;
	
	private MsgTypeEnum(byte value){
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}
	
	
	
	
}
