package com.gosuncn.netty.core.accepter;

import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.model.DefaultDTO;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 默认客户端消息接收器
 */
public class DefaultClientMsgAccepter extends SimpleChannelInboundHandler<DefaultDTO>{

	
	/**
	 * 请求回调，每一次接收到请求数据均会回调该方法
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DefaultDTO msg) throws Exception {
		
		//LoggerUtils.info("客户端接收到消息-{}",JsonUtils.toJsonString(msg));
		
		LoggerUtils.info("客户端接收到消息体-{}",new String(msg.getBody(),"UTF-8"));
		
	}
	
	
	/**
	 * 建立连接时回调方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		LoggerUtils.info("客户端与远程主机建立连接");
		
		super.channelActive(ctx);
	}
	
	
	
	/**
	 * 断开连接时回调方法
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
		LoggerUtils.info("客户端与远程主机断开连接");
		
		super.channelInactive(ctx);
	}
	
	
	/**
	 * 抛出异常时回调方法
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		LoggerUtils.info("抛出异常-{}",cause.getMessage(),cause);
		
		super.exceptionCaught(ctx, cause);
	}

}
