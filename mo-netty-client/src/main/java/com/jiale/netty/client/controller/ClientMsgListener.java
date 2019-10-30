package com.jiale.netty.client.controller;

import com.jiale.netty.client.model.ResponseModel;
import com.jiale.netty.core.annotation.MoMsgListener;
import com.jiale.netty.core.model.ResponseDTO;
import com.jiale.netty.core.util.JsonUtils;
import com.jiale.netty.core.util.LoggerUtils;
import com.jiale.netty.core.listener.MsgListener;
import io.netty.channel.ChannelHandlerContext;

@MoMsgListener
public class ClientMsgListener implements MsgListener{

	@Override
	public void onMsgRead(ChannelHandlerContext ctx, Object msg) {

		ResponseDTO responseDTO = (ResponseDTO) msg;
		ResponseModel responseModel = new ResponseModel();
		responseModel.readFromBytes(responseDTO.body);
		LoggerUtils.info("客户端接收到响应-{}",JsonUtils.toJsonString(responseModel));
		//LoggerUtils.info("客户端接收到响应-{}",JsonUtils.toJsonString(msg));
	}

	@Override
	public void onChannelConnected(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChannelDisconnect(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO Auto-generated method stub
		
	}

}
