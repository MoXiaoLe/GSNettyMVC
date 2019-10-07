package com.jiale.netty.core.codec;

import com.jiale.netty.core.model.ResponseDTO;
import com.jiale.netty.core.util.LoggerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author mojiale66@163.com
 * @date 2019/10/7
 * @description 响应编码器
 * ----------------------------------------------------------------
 * |报文开始标志  | 报文类型  | 响应状态码  |  报文体长度  |  报文体  |
 * |startFlag   |  msgType  | statusCode |  bodyLength |  body    |
 * |int         |  byte     | short      |  int        |  byte[]  |
 * ----------------------------------------------------------------
 */
public class ResponseEncoder extends MessageToByteEncoder<ResponseDTO> {


    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ResponseDTO responseDTO, ByteBuf out) throws Exception {

        LoggerUtils.cacheLogger(ResponseEncoder.class).debug("响应编码器开始编码");

        if(responseDTO.body == null){
            throw new NullPointerException("body not allow null");
        }
        if(responseDTO.body.length != responseDTO.bodyLength){
            responseDTO.bodyLength = responseDTO.body.length;
        }
        out.writeInt(responseDTO.startFlag);
        out.writeByte(responseDTO.msgType);
        out.writeShort(responseDTO.statusCode);
        out.writeInt(responseDTO.bodyLength);
        out.writeBytes(responseDTO.body);

        LoggerUtils.cacheLogger(ResponseEncoder.class).debug("响应编码器结束编码");

    }
}
