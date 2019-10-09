package com.jiale.netty.core.util;

import com.jiale.netty.core.model.*;

/**
 * @author mojiale@bluemoon.com.cn
 * @date 2019/10/8
 * @description 响应工具类
 */
public class ResponseUtils {

    public static void responseNotFound(MoResponse moResponse){

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.startFlag = CodecConst.START_FLAG;
        responseDTO.msgType = SystemConst.FORM;
        responseDTO.statusCode = ResponseStatusCodeEnum.NOT_FOND.getCode();
        responseDTO.body = ResponseStatusCodeEnum.NOT_FOND.getMsg().getBytes(SystemConst.CHARSET);
        responseDTO.bodyLength = responseDTO.body.length;
        moResponse.getChannel().writeAndFlush(responseDTO);

    }

    public static void responseError(MoResponse moResponse){

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.startFlag = CodecConst.START_FLAG;
        responseDTO.msgType = SystemConst.FORM;
        responseDTO.statusCode = ResponseStatusCodeEnum.ERROR.getCode();
        responseDTO.body = ResponseStatusCodeEnum.ERROR.getMsg().getBytes(SystemConst.CHARSET);
        responseDTO.bodyLength = responseDTO.body.length;
        moResponse.getChannel().writeAndFlush(responseDTO);

    }

    public static void responseSuccess(MoResponse moResponse,Object result){

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.startFlag = CodecConst.START_FLAG;
        responseDTO.msgType = SystemConst.SERIALIZER;
        responseDTO.statusCode = ResponseStatusCodeEnum.OK.getCode();
        if(result instanceof Serializer){
            responseDTO.body = ((Serializer) result).getBytes();
        }else{
            throw new RuntimeException("body format not support");
        }
        responseDTO.bodyLength = responseDTO.body.length;
        moResponse.getChannel().writeAndFlush(responseDTO);

    }






}
