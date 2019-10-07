package com.jiale.netty.core.accepter;

import com.jiale.netty.core.util.JsonUtils;
import com.jiale.netty.core.common.IocContainer;
import com.jiale.netty.core.dispatcher.DefaultServerMsgDispatcher;
import com.jiale.netty.core.model.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 默认服务端消息接收器
 */
public class RequestAccepter extends SimpleChannelInboundHandler<RequestDTO>{
	
	private MsgListener msgListener = IocContainer.getMsgListener("serverMsgListener"); 
	
	/**
	 * 请求回调，每一次接收到请求数据均会回调该方法
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestDTO msg) throws Exception {

		System.out.println(msg);

		/*Channel channel = ctx.channel();
		DefaultHeader header = msg.getHeader();
		byte[] body = msg.getBody();
		if(channel == null || header == null){
			throw new Exception("channelRead0 未知错误");
		}
		
		MoRequest request = MoRequest.newInstance(channel, header, body);
		MoResponse response = MoResponse.newInstance(channel, null, null);
		service(request, response);*/
		
		if(msgListener != null){
			msgListener.onMsgRead(ctx, msg);
		}
		
	}
	
	
	/**
	 * 建立连接时回调方法
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		// 创建并缓存session
		Channel channel = ctx.channel();
		if(channel != null){
			MoSession session = new MoSessionImpl(channel);
			IocContainer.putSession(session);
		}else{
			throw new Exception("创建session异常");
		}
		
		if(msgListener != null){
			msgListener.onChannelConnected(ctx);
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
		
		if(msgListener != null){
			msgListener.onChannelDisconnect(ctx);
		}
		
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
	
	protected void service(MoRequest goRequest, MoResponse goResponse) {
		
		// 对 body 进行解码
		byte[] body = goRequest.getBody();
		if(body != null && body.length > 0){
			DefaultRequestHeader header = (DefaultRequestHeader)goRequest.getHeader();
			byte bodyType = header.getRequestType();
			if(bodyType == BodyTypeInface.JSON){
				JsonUtils.Node node = getNodeFromBody(body);
				goRequest.setParamsNode(node);
				goRequest.setJsonStrBytes(body);
			}else if(bodyType == BodyTypeInface.FORM){
				Map<String,String[]> paramsMap = getParamsMapFromBody(body);
				goRequest.setParamsMap(paramsMap);
			}else if(bodyType == BodyTypeInface.SERIALIZER){
				goRequest.setParamsSerializerBytes(body);
			}else{
				throw new RuntimeException("不支持的body类型");
			}
		}
		
		// 把消息传递到 dispathcher 中
		DefaultServerMsgDispatcher.newInstance().dispathcher(goRequest, goResponse);
	}
	
	private JsonUtils.Node getNodeFromBody(byte[] body){
		
		String data = new String(body,Charset.forName("UTF-8"));
		JsonUtils.Node node = JsonUtils.getNodeFromJsonString(data);
		return node;
	}
	
	private Map<String, String[]> getParamsMapFromBody(byte[] body){
		
		String data = new String(body,Charset.forName("UTF-8"));
		String[] keyValues = data.replaceAll("&+", "&").split("&");
		Map<String, List<String>> tempMap = new HashMap<String, List<String>>();
		for(String keyValue : keyValues){
			if(!keyValue.contains("=")){
				continue;
			}
			int index = keyValue.indexOf("=");
			String key = keyValue.substring(0,index);
			String value = keyValue.substring(index+1);
			List<String> valueList = tempMap.get(key);
			if(valueList == null){
				valueList = new ArrayList<String>();
				valueList.add(value);
				tempMap.put(key, valueList);
			}else{
				valueList.add(value);
			}
		}
		Map<String, String[]> paramsMap = new HashMap<String,String[]>();
		for(Entry<String, List<String>> entry : tempMap.entrySet()){
			List<String> list = entry.getValue();
			String[] arr = new String[list.size()];
			arr = list.toArray(arr);
			paramsMap.put(entry.getKey(), arr);
		}
		
		return paramsMap;
	}

}
