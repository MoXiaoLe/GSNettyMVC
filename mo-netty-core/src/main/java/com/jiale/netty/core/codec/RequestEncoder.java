package com.jiale.netty.core.codec;

import com.jiale.netty.core.model.RequestDTO;
import com.jiale.netty.core.util.LoggerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月21日
 * @description 请求编码器
 * --------------------------------------------------------------------------
 * |报文开始标志  | 报文类型  | 请求url长度  | 报文体长度 | 请求url  |  报文体  |
 * |startFlag   |  msgType  | urlLength   | bodyLength|  url     |  body   |
 * |int         |  byte     | short       | int       |  byte[]  |  byte[] |
 * --------------------------------------------------------------------------
 */
public class RequestEncoder extends MessageToByteEncoder<RequestDTO>{

	@Override
	protected void encode(ChannelHandlerContext ctx, RequestDTO requestDTO, ByteBuf out) throws Exception {
		
		LoggerUtils.cacheLogger(RequestEncoder.class).debug("编码器开始编码");

		if(requestDTO.url == null || requestDTO.body == null){
			throw new NullPointerException("url or body not allow null");
		}
		if(requestDTO.url.length != requestDTO.urlLength){
			requestDTO.urlLength = (short) requestDTO.url.length;
		}
		if(requestDTO.body.length != requestDTO.bodyLength){
			requestDTO.bodyLength = requestDTO.body.length;
		}
		out.writeInt(requestDTO.startFlag);
		out.writeByte(requestDTO.msgType);
		out.writeShort(requestDTO.urlLength);
		out.writeInt(requestDTO.bodyLength);
		out.writeBytes(requestDTO.url);
		out.writeBytes(requestDTO.body);

		LoggerUtils.cacheLogger(RequestEncoder.class).debug("编码器结束编码");
		
	}

}
