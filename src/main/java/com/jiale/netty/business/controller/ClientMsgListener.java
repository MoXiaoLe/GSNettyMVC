package com.jiale.netty.business.controller;

import com.jiale.netty.common.util.JsonUtils;
import com.jiale.netty.common.util.LoggerUtils;
import com.jiale.netty.core.accepter.MsgListener;
import com.jiale.netty.core.annotation.MoClientMsgListener;
import com.jiale.netty.core.model.DefaultDTO;

import io.netty.channel.ChannelHandlerContext;

@MoClientMsgListener
public class ClientMsgListener implements MsgListener{

	@Override
	public void onMsgRead(ChannelHandlerContext ctx, Object msg) {
		
		LoggerUtils.info("客户端接收到响应-{}",JsonUtils.toJsonString(msg));
		LoggerUtils.info("客户端接收到响应body-{}",new String(((DefaultDTO)msg).getBody()));
		
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
