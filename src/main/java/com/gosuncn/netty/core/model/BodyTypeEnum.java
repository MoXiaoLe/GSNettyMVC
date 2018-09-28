package com.gosuncn.netty.core.model;

public enum BodyTypeEnum {

	/**body以json形式解编码*/
	JSON((byte)0),
	
	/**body以基本类型形式解编码*/
	SERIALIZER((byte)1),
	
	/**body以表单形式解编码*/
	FORM((byte)2);
	
	private byte value;
	
	private BodyTypeEnum(byte value){
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}
	
}
