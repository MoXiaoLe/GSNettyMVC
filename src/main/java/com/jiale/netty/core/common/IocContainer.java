package com.jiale.netty.core.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jiale.netty.common.util.INetUtils;
import com.jiale.netty.core.accepter.MsgListener;
import com.jiale.netty.core.model.GSContext;
import com.jiale.netty.core.model.GSSession;


/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description IOC 容器用于保存需要的一些组件对象和域对象
 */
public class IocContainer {
	
	/**应用上下文对象*/
	private static GSContext goContext;
	/**会话集合 */
	private static Map<String,GSSession> sessionHolderMap = new ConcurrentHashMap<String,GSSession>(); 
	/**执行器集合*/
	private static Map<String,InvokerHolder> invokerHolderMap = new ConcurrentHashMap<String, InvokerHolder>();
	/**消息回调监听器*/
	private static Map<String, MsgListener> msgListenerHolderMap = new ConcurrentHashMap<String, MsgListener>();
	
	
	/**初始化上下文对象*/
	public static void initContext(int port){
		goContext = GSContext.newInstance(INetUtils.getLocalIP(),port);
	}
	
	public static void putSession(GSSession session){
		
		if(session != null){
			sessionHolderMap.put(session.getChannelId(), session);
		}
	}
	
	public static GSSession getSession(String channelId){
		
		return sessionHolderMap.get(channelId);
	}
	
	public static void removeSession(String channelId){
		
		sessionHolderMap.remove(channelId);
		
	}
	
	public static void putInvokerHolder(String url,InvokerHolder invokerHolder){
		
		InvokerHolder invoker =  invokerHolderMap.get(url);
		if(invoker != null){
			throw new RuntimeException("url 映射重复冲突");
		}
		invokerHolderMap.put(url, invokerHolder);
	}
	
	public static InvokerHolder getInvokerHolder(String url){
		
		return invokerHolderMap.get(url);
		
	}

	public static GSContext getGoContext() {
		return goContext;
	}
	
	public static void putMsgListener(String key,MsgListener msgListener){
		
		MsgListener listener = msgListenerHolderMap.get(key);
		if(listener != null){
			throw new RuntimeException(key + "-消息回调监听器重复");
		}
		msgListenerHolderMap.put(key, msgListener);
	}
	
	public static MsgListener getMsgListener(String key){
		
		return msgListenerHolderMap.get(key);
	}
	
}
