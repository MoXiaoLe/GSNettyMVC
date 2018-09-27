package com.gosuncn.netty.core.accepter;

import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.common.GoRequest;
import com.gosuncn.netty.core.common.GoResponse;
import com.gosuncn.netty.core.model.DefaultDTO;
import com.gosuncn.netty.core.model.DefaultHeader;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 默认服务端消息接收器
 */
public class DefaultServerMsgAccepter extends SimpleChannelInboundHandler<DefaultDTO>{
	
	/**
	 * 请求回调，每一次接收到请求数据均会回调该方法
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DefaultDTO msg) throws Exception {
		
		Channel channel = ctx.channel();
		DefaultHeader header = msg.getHeader();
		byte[] body = msg.getBody();
		
		GoRequest request = GoRequest.newInstance(channel, header, body);
		GoResponse response = GoResponse.newInstance(channel, null, null);
		service(request, response);
		
	}
	
	
	/**
	 * 建立连接时回调方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		// 创建 session 
		
		super.channelActive(ctx);
	}
	
	
	
	/**
	 * 断开连接时回调方法
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
		// 移除session
		
		super.channelInactive(ctx);
	}
	
	
	/**
	 * 抛出异常时回调方法
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		// 异常处理
		
		super.exceptionCaught(ctx, cause);
	}
	
	protected void service(GoRequest goRequest,GoResponse goResponse) {
		
		//DefaultRequestHeader header = (DefaultRequestHeader)goRequest.getHeader();
		
		LoggerUtils.info("接收到消息-{}",goRequest.getBody());
		
	}

}
