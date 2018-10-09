package com.gosuncn.netty.core.model;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 解码编码常量集合
 */
public interface CodecConstInface {

	/**报文头标志*/
	final int START_FLAG = 199554;
	
	/**
	 * 报文基础长度:
	 * 报文头标志变量 （4字节）+ 报文类型（1字节）+ 报文头长度变量（2字节） + 报文体长度变量（4字节）
	 * 
	 */
	final int BASE_MESSAGE_LEN = 11;
	
}
