package com.jiale.netty.core.model;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月21日
 * @description 报文类型枚举
 */
public interface MsgTypeInface {
	
	/**请求*/
	final byte REQUEST = (byte)0;
	
	/**响应*/
	final byte RESPONSE = (byte)1;
	
	
}
