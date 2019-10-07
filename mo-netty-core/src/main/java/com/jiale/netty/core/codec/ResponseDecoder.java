package com.jiale.netty.core.codec;

import com.jiale.netty.core.util.LoggerUtils;
import com.jiale.netty.core.model.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 响应解码器
 * ----------------------------------------------------------------
 * |报文开始标志  | 报文类型  | 响应状态码  |  报文体长度  |  报文体  |
 * |startFlag   |  msgType  | statusCode |  bodyLength |  body    |
 * |int         |  byte     | short      |  int        |  byte[]  |
 * ----------------------------------------------------------------
 */
public class ResponseDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf buff, List<Object> resultList) throws Exception {
				
		LoggerUtils.cacheLogger(ResponseDecoder.class).debug("响应解码器开始解码");
		
		while(buff.readableBytes() >= CodecConst.BASE_MESSAGE_LEN){
			// 找到头标志为止
			while(true){
				// 标志开始读位置
				buff.markReaderIndex();
				if(buff.readInt() == CodecConst.START_FLAG){
					break;
				}
				buff.resetReaderIndex();
				buff.readByte();
				
				// 可读字节少于基本长度还找不到则返回
				if(buff.readableBytes() < CodecConst.BASE_MESSAGE_LEN){
					return;
				}
			}
			
			// 到这里意味着找到了头标志，开始解码
			byte msgType = buff.readByte();
			short statusCode = buff.readShort();
			int bodyLength = buff.readInt();
			if(buff.readableBytes() >= bodyLength){
				byte[] body = new byte[bodyLength];
				buff.readBytes(body);
				ResponseDTO responseDTO = new ResponseDTO();
				responseDTO.startFlag = CodecConst.START_FLAG;
				responseDTO.msgType = msgType;
				responseDTO.statusCode = statusCode;
				responseDTO.bodyLength = bodyLength;
				responseDTO.body = body;
				resultList.add(responseDTO);
			}else{
				// 可读长度不足回滚并结束本次解码
				buff.resetReaderIndex();
				return;
			}
		}

		LoggerUtils.cacheLogger(ResponseDecoder.class).debug("响应解码器解码结束");

	}

}
