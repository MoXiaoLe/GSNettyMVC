package com.jiale.netty.core.codec;

import com.jiale.netty.core.model.CodecConst;
import com.jiale.netty.core.model.RequestDTO;
import com.jiale.netty.core.util.LoggerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author mojiale66@163.com
 * @date 2019/10/7
 * @description 请求解码器
 * --------------------------------------------------------------------------
 * |报文开始标志  | 报文类型  | 请求url长度  | 报文体长度 | 请求url  |  报文体  |
 * |startFlag   |  msgType  | urlLength   | bodyLength|  url     |  body   |
 * |int         |  byte     | short       | int       |  byte[]  |  byte[] |
 * --------------------------------------------------------------------------
 */
public class RequestDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buff, List<Object> resultList) throws Exception {

        LoggerUtils.cacheLogger(RequestDecoder.class).debug("请求解码器开始解码");

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
            short urlLength = buff.readShort();
            int bodyLength = buff.readInt();
            int totalLength = urlLength + bodyLength;
            if(buff.readableBytes() >= totalLength){
                byte[] url = new byte[urlLength];
                byte[] body = new byte[bodyLength];
                buff.readBytes(url);
                buff.readBytes(body);
                RequestDTO requestDTO = new RequestDTO();
                requestDTO.startFlag = CodecConst.START_FLAG;
                requestDTO.msgType = msgType;
                requestDTO.urlLength = urlLength;
                requestDTO.bodyLength = bodyLength;
                requestDTO.url = url;
                requestDTO.body = body;
                resultList.add(requestDTO);
            }else{
                // 可读长度不足回滚并结束本次解码
                buff.resetReaderIndex();
                return;
            }
        }

        LoggerUtils.cacheLogger(RequestDecoder.class).debug("请求解码器解码结束");


    }





}
