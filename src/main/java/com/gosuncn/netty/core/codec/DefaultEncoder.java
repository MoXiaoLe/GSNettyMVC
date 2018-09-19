package com.gosuncn.netty.core.codec;

import com.gosuncn.netty.core.model.DefaultResponseDTO;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 默认编码器
 */
public class DefaultEncoder extends MessageToByteEncoder<DefaultResponseDTO>{

	@Override
	protected void encode(ChannelHandlerContext arg0, DefaultResponseDTO arg1, ByteBuf arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
