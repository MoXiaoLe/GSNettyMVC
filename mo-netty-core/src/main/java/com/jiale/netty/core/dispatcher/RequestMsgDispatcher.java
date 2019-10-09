package com.jiale.netty.core.dispatcher;

import com.jiale.netty.core.util.*;
import com.jiale.netty.core.util.JsonUtils.Node;
import com.jiale.netty.core.common.InvokerHolder;
import com.jiale.netty.core.common.IocContainer;
import com.jiale.netty.core.model.*;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月28日
 * @description 请求消息分发器
 */
public class RequestMsgDispatcher {
	
	private static RequestMsgDispatcher dispatcher;
	
	private RequestMsgDispatcher(){
		
	}

	/**
	 * 饿汉式单例
	 * @return
	 */
	public static RequestMsgDispatcher newInstance(){
		
		if(dispatcher == null){
			synchronized (RequestMsgDispatcher.class) {
				if(dispatcher == null){
					dispatcher = new RequestMsgDispatcher();
				}
			}
		}
		return dispatcher;
	}

	/**
	 * 请求分发
	 * @param request
	 * @param response
	 */
	public void dispatch(MoRequest request, MoResponse response) throws Exception{

		String url = ParseUtils.parseUrl(request.getUrl());
		InvokerHolder invoker = IocContainer.getInvokerHolder(url);
		if(invoker == null){
			ResponseUtils.responseNotFound(response);
		}else{
			try {
				Method method = invoker.getMethod();
				LocalVariableTableParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
				String[] paramsNames = nameDiscoverer.getParameterNames(method);
				Class<?>[] paramsClazz = method.getParameterTypes();
				Object[] params = new Object[paramsClazz.length];
				for(int i=0;i<paramsClazz.length;i++){
					params[i] =  ReflectUtils.buildObjectByClazz(paramsNames[i],paramsClazz[i],request,response);
				}
				Object retResult = method.invoke(invoker.getObj(),params);
				if(retResult != null){
					ResponseUtils.responseSuccess(response,retResult);
				}
			} catch (Exception e) {
				LoggerUtils.cacheLogger(RequestMsgDispatcher.class).error("服务端执行器处理异常-{}", e);
				ResponseUtils.responseError(response);
			}
		}
	}

}
