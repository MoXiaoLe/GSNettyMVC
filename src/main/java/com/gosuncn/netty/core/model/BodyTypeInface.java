package com.gosuncn.netty.core.model;

public interface BodyTypeInface {

	/**body以json形式解编码*/
	final byte JSON = (byte)0;
	
	/**body以基本类型形式解编码*/
	final byte SERIALIZER = (byte)1;
	
	/**body以表单形式解编码*/
	final byte FORM = (byte)2;
	
}
