package com.gosuncn.netty.core.codec;

import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.model.DefaultDTO;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月21日
 * @description 默认请求编码器
 */
public class DefaultEncoder extends MessageToByteEncoder<DefaultDTO>{

	@Override
	protected void encode(ChannelHandlerContext ctx, DefaultDTO msg, ByteBuf out) throws Exception {
		
		LoggerUtils.debug("编码器开始编码");
		
		if(msg != null && msg.getHeader() != null){
			
			short headerLen = msg.getHeaderLen();
			int bodyLen = msg.getBodyLen();
			byte[] headerBytes = msg.getHeader().getBytes();
			byte[] bodyBytes = msg.getBody();
			
			// 修正长度
			if(headerLen != headerBytes.length){
				headerLen = (short) headerBytes.length;
			}
			if(bodyBytes != null){
				if(bodyLen != bodyBytes.length){
					bodyLen = bodyBytes.length;
				}
			}else{
				bodyLen = 0;
			}
			
			out.writeInt(msg.getStartFlag());
			out.writeByte(msg.getMsgType());
			out.writeShort(headerLen);
			out.writeInt(bodyLen);
			out.writeBytes(headerBytes);
			if(bodyLen > 0){
				out.writeBytes(bodyBytes);
			}
		}
		
		LoggerUtils.debug("编码器结束编码");
		
	}

}
