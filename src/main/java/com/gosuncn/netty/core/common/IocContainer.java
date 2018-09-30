package com.gosuncn.netty.core.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gosuncn.netty.common.util.INetUtils;
import com.gosuncn.netty.core.model.GoContext;
import com.gosuncn.netty.core.model.GoSession;


/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description IOC 容器用于保存需要的一些组件对象和域对象
 */
public class IocContainer {
	
	/**应用上下文对象*/
	private static GoContext goContext;
	/**会话集合 */
	private static Map<String,GoSession> sessionHolderMap = new ConcurrentHashMap<String,GoSession>(); 
	/**执行器集合*/
	private static Map<String,InvokerHolder> invokerHolderMap = new ConcurrentHashMap<String, InvokerHolder>();
	/**初始化上下文对象*/
	public static void initContext(int port){
		goContext = GoContext.newInstance(INetUtils.getLocalIP(),port);
	}
	
	public static void putSession(GoSession session){
		
		if(session != null){
			sessionHolderMap.put(session.getChannelId(), session);
		}
	}
	
	public static GoSession getSession(String channelId){
		
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

	public static GoContext getGoContext() {
		return goContext;
	}
	
	
}
