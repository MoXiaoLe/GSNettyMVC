package com.gosuncn.netty.core.dispatcher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.gosuncn.netty.common.util.JsonUtils.Node;
import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.common.GoContext;
import com.gosuncn.netty.core.common.GoRequest;
import com.gosuncn.netty.core.common.GoResponse;
import com.gosuncn.netty.core.common.GoSession;
import com.gosuncn.netty.core.common.InvokerHolder;
import com.gosuncn.netty.core.common.IocContainer;
import com.gosuncn.netty.core.model.DefaultRequestHeader;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月28日
 * @description 消息分发器
 */
public class DefaultServerMsgDispatcher {
	
	private static DefaultServerMsgDispatcher dispatcher;
	
	private DefaultServerMsgDispatcher(){
		
	}
	
	public static DefaultServerMsgDispatcher newInstance(){
		
		if(dispatcher == null){
			synchronized (DefaultServerMsgDispatcher.class) {
				if(dispatcher == null){
					dispatcher = new DefaultServerMsgDispatcher();
				}
			}
		}
		
		return dispatcher;
	}
	
	public void dispathcher(GoRequest request,GoResponse response){
		
		DefaultRequestHeader header = (DefaultRequestHeader) request.getHeader();
		String url = header.getUrl().trim();
		String contextPath = request.getGoContext().getContextPath();
		url = url.replaceFirst(contextPath,"").replaceAll("/+", "/");
		InvokerHolder invoker = IocContainer.getInvokerHolder(url);
		if(invoker == null){
			LoggerUtils.warn("找不到对应的执行方法");
		}else{
			try {
				Method method = invoker.getMethod();
				Class<?>[] paramsClazzs = method.getParameterTypes();
				Object[] params = new Object[paramsClazzs.length];
				for(int i=0;i<paramsClazzs.length;i++){
					params[i] =  buildByClazz(paramsClazzs[i],request,response);
				}
				method.invoke(invoker.getObj(),params);
			} catch (Exception e) {
				LoggerUtils.warn("执行出错-{}",e.getMessage(),e);
			}
		}
	}
	
	private Object buildByClazz(Class<?> clazz,GoRequest request,GoResponse response){
		
		if(clazz == GoRequest.class){
			return request;
		}else if(clazz == GoResponse.class){
			return response;
		}else if(clazz == GoContext.class){
			return IocContainer.getGoContext();
		}else if(clazz == GoSession.class){
			return request.getSession();
		}else{
			try {
				Object obj = clazz.newInstance();
				Field[] fields = clazz.getDeclaredFields();
				for(Field field : fields){
					fillValue4Field(field,obj,request);
				}
				return obj;
			} catch (Exception e) {
				LoggerUtils.warn("创建参数异常-{}",e.getMessage(),e);
			} 		
			return null;
		}
	}
	
	/**
	 * TODO 一塌糊涂，想到解决办法再搞
	 * @param field
	 * @param obj
	 * @param request
	 * @throws Exception
	 */
	private void fillValue4Field(Field field,Object obj,GoRequest request) throws Exception{
		
		field.setAccessible(true);
		Class<?> clazz = field.getClass();
		Object value = null;
		String key = field.getName();
		Node node = request.getParamsNode();
		if(key.equals(node.getKey())){
			if(clazz == short.class || clazz == Short.class){
				
			}else if(clazz == int.class || clazz == Integer.class){
				
			}else if(clazz == long.class || clazz == Long.class){
				
			}else if(clazz == float.class || clazz == Float.class){
				
			}else if(clazz == double.class || clazz == Double.class){
				
			}else if(clazz == boolean.class || clazz == Boolean.class){
				
			}else if(clazz == byte.class || clazz == Byte.class){
				
			}else if(clazz == String.class){
				
			}else{
				
			}
			field.set(obj, value);
		}else{
			
		}
	}
	

}
