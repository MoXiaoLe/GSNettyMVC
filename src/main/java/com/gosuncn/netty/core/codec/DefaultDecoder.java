package com.gosuncn.netty.core.codec;

import java.util.List;

import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.model.CodecConstInface;
import com.gosuncn.netty.core.model.DefaultDTO;
import com.gosuncn.netty.core.model.DefaultHeader;
import com.gosuncn.netty.core.model.DefaultRequestHeader;
import com.gosuncn.netty.core.model.DefaultResponseHeader;
import com.gosuncn.netty.core.model.MsgTypeInface;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 默认解码器
 * ------------------------------------------------------------------------
 * | 报文开始标志  | 报文类型     | 报文头长度      | 报文体长度   | 报文头                      |  报文体      |
 * | startFlag | msgType | headerLen | bodyLen  | header        |  body   |
 * | int       | byte    | short     | int      | DefaultHeader |  byte[] |
 * -------------------------------------------------------------------------
 */
public class DefaultDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf buff, List<Object> resultList) throws Exception {
				
		LoggerUtils.debug("解码器开始解码");
		
		while(buff.readableBytes() >= CodecConstInface.BASE_MESSAGE_LEN){
			// 找到头标志为止
			while(true){
				// 标志开始读位置
				buff.markReaderIndex();
				if(buff.readInt() == CodecConstInface.START_FLAG){
					break;
				}
				buff.resetReaderIndex();
				buff.readByte();
				
				// 可读字节少于基本长度还找不到则返回
				if(buff.readableBytes() < CodecConstInface.BASE_MESSAGE_LEN){
					return;
				}
			}
			
			// 到这里意味着找到了头标志，开始解码
			byte msgType = buff.readByte();
			short headerLen = buff.readShort();
			int bodyLen = buff.readInt();
			int len = bodyLen + headerLen;
			DefaultHeader header = null;
			if(len > 0 && buff.readableBytes() >= len){
				byte[] headerBytes = new byte[headerLen];
				buff.readBytes(headerBytes);
				if(msgType == MsgTypeInface.REQUEST){
					// 请求头
					header = new DefaultRequestHeader();
				}else if(msgType == MsgTypeInface.RESPONSE){
					// 响应头
					header = new DefaultResponseHeader();
				}else{
					throw new RuntimeException("不支持的报文类型");
				}
				header.readFromBytes(headerBytes);
				
				// 报文体
				byte[] bodyBytes = new byte[bodyLen];
				buff.readBytes(bodyBytes);
				
				// 组装对象
				DefaultDTO dto = new DefaultDTO();
				dto.setMsgType(msgType);
				dto.setHeaderLen(headerLen);
				dto.setBodyLen(bodyLen);
				dto.setHeader(header);
				dto.setBody(bodyBytes);
				resultList.add(dto);
				
			}else{// 可读长度不足回滚并结束本次解码
				buff.resetReaderIndex();
				return;
			}
		}
		
		LoggerUtils.debug("解码器解码结束");
		
	}

}
