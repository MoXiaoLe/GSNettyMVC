package com.gosuncn.netty.core.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月19日
 * @description buff 工厂类
 */
public class BufferFactory {

	/**netty提供的用于创建byteBuf的接口，不同的实现类，底层byteBuf的数据结构不一样*/
	private final static ByteBufAllocator byteBufAllocator = ByteBufAllocator.DEFAULT;
	
	public static ByteBuf buildBuff(){
		
		return byteBufAllocator.heapBuffer();
	}
	
	public static ByteBuf buildBuff(byte[] data){
		
		ByteBuf byteBuf = byteBufAllocator.heapBuffer();
		byteBuf.writeBytes(data);
		return byteBuf;
	}
	
	
}
