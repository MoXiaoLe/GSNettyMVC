package com.gosuncn.netty.core.accepter;

import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年10月9日
 * @description 消息回调监听器
 */
public interface MsgListener {
	
	void onMsgRead(ChannelHandlerContext ctx,Object msg);
	
	void onChannelConnected(ChannelHandlerContext ctx);
	
	void onChannelDisconnect(ChannelHandlerContext ctx);
	
	void onExceptionCaught(ChannelHandlerContext ctx, Throwable cause);
	
}
