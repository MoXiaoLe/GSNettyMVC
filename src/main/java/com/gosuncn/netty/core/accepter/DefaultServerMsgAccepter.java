package com.gosuncn.netty.core.accepter;

import java.nio.charset.Charset;

import com.gosuncn.netty.common.util.JsonUtils;
import com.gosuncn.netty.common.util.JsonUtils.Node;
import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.common.GoRequest;
import com.gosuncn.netty.core.common.GoResponse;
import com.gosuncn.netty.core.common.GoSession;
import com.gosuncn.netty.core.common.GoSessionImpl;
import com.gosuncn.netty.core.common.IocContainer;
import com.gosuncn.netty.core.dispatcher.DefaultServerMsgDispatcher;
import com.gosuncn.netty.core.model.BodyTypeEnum;
import com.gosuncn.netty.core.model.DefaultDTO;
import com.gosuncn.netty.core.model.DefaultHeader;
import com.gosuncn.netty.core.model.DefaultRequestHeader;

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
		if(channel == null || header == null){
			throw new Exception("channelRead0 未知错误");
		}
		
		GoRequest request = GoRequest.newInstance(channel, header, body);
		GoResponse response = GoResponse.newInstance(channel, null, null);
		service(request, response);
		
	}
	
	
	/**
	 * 建立连接时回调方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		// 创建并缓存session
		Channel channel = ctx.channel();
		if(channel != null){
			GoSession session = new GoSessionImpl(channel);
			IocContainer.putSession(session);
		}else{
			throw new Exception("创建session异常");
		}
		
		super.channelActive(ctx);
	}
	
	
	
	/**
	 * 断开连接时回调方法
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
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
		LoggerUtils.warn("抛出异常-{}",cause.getMessage(),cause);
		//super.exceptionCaught(ctx, cause);
		
	}
	
	protected void service(GoRequest goRequest,GoResponse goResponse) {
		
		// 对 body 进行解码
		DefaultRequestHeader header = (DefaultRequestHeader)goRequest.getHeader();
		byte bodyType = header.getRequestType();
		if(bodyType == BodyTypeEnum.JSON.getValue()){
			byte[] body = goRequest.getBody();
			if(body != null && body.length > 0){
				String data = new String(body,Charset.forName("UTF-8"));
				Node node = JsonUtils.getNodeFromJsonString(data);
				goRequest.setParamsNode(node);
			}
		}else{
			throw new RuntimeException("不支持的body类型");
		}
		// 把消息传递到 dispathcher 中
		DefaultServerMsgDispatcher.newInstance().dispathcher(goRequest, goResponse);		
	}

}
