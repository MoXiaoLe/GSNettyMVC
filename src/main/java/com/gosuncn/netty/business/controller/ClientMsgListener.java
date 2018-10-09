package com.gosuncn.netty.business.controller;

import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.accepter.MsgListener;
import com.gosuncn.netty.core.annotation.GoClientMsgListener;

import io.netty.channel.ChannelHandlerContext;

@GoClientMsgListener
public class ClientMsgListener implements MsgListener{

	@Override
	public void onMsgRead(ChannelHandlerContext ctx, Object msg) {
		LoggerUtils.info("客户端收到消息");
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
