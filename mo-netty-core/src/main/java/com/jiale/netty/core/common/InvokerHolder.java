package com.jiale.netty.core.common;

import java.lang.reflect.Method;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月28日
 * @description 执行器对象
 */
public class InvokerHolder {

	/**目标对象*/
	private Object obj;
	
	/**目标方法*/
	private Method method;

	public InvokerHolder(Object obj, Method method) {
		super();
		this.obj = obj;
		this.method = method;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
	public static InvokerHolder valueOf(Object obj,Method method){
		
		InvokerHolder invokerHolder = new InvokerHolder(obj, method);
		return invokerHolder;
		
	}
	
}
