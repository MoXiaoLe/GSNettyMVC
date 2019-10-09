package com.jiale.netty.core.accepter;

import com.jiale.netty.core.listener.MsgListener;
import com.jiale.netty.core.common.IocContainer;
import com.jiale.netty.core.dispatcher.RequestMsgDispatcher;
import com.jiale.netty.core.model.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 请求消息接收器
 */
public class RequestAccepter extends SimpleChannelInboundHandler<RequestDTO>{
	
	private MsgListener msgListener = IocContainer.getMsgListener("msgListener");

	/**
	 * 请求回调，每一次接收到请求数据均会回调该方法
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestDTO msg) throws Exception{

		if(msgListener != null){
			msgListener.onMsgRead(ctx, msg);
		}

		MoRequest moRequest = MoRequest.newInstance(ctx,msg);
		MoResponse moResponse = MoResponse.newInstance(ctx,null);
		service(moRequest, moResponse);
	}

	/**
	 * 建立连接时回调方法
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx)throws Exception{

		if(msgListener != null){
			msgListener.onChannelConnected(ctx);
		}
		// 创建并缓存session
		Channel channel = ctx.channel();
		if(channel != null){
			MoSession session = new MoSessionImpl(channel);
			IocContainer.putSession(session);
		}

		super.channelActive(ctx);
	}

	/**
	 * 断开连接时回调方法
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		if(msgListener != null){
			msgListener.onChannelDisconnect(ctx);
		}
		// 移除session
		Channel channel = ctx.channel();
		String channelId = channel.id().asLongText();
		IocContainer.removeSession(channelId);

		super.channelInactive(ctx);
	}
	
	
	/**
	 * 抛出异常时回调方法
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		// 异常处理
		if(msgListener != null){
			msgListener.onExceptionCaught(ctx, cause);
		}
		
	}

	/**
	 * 请求逻辑处理
	 * @param moRequest
	 * @param moResponse
	 */
	protected void service(MoRequest moRequest, MoResponse moResponse) throws Exception{

		// 把消息传递到 RequestMsgDispatcher 中
		RequestMsgDispatcher.newInstance().dispatch(moRequest, moResponse);
	}
	

}
