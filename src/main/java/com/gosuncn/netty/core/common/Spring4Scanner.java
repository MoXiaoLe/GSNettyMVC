package com.gosuncn.netty.core.common;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.accepter.MsgListener;
import com.gosuncn.netty.core.annotation.GoClientMsgListener;
import com.gosuncn.netty.core.annotation.GoController;
import com.gosuncn.netty.core.annotation.GoRequestMapping;
import com.gosuncn.netty.core.annotation.GoServerMsgListener;
/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月28日
 * @description bean 扫描器
 */
@Component
public class Spring4Scanner implements BeanPostProcessor{

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		
		// 扫描GoController 注解
		scannerGoController(bean);
		
		// 扫描 GoClientMsgListener 、 GoServerMsgListener 注解
		scannerMsgListener(bean);
		
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}
	
	
	private void scannerMsgListener(Object bean){
		
		if(bean instanceof MsgListener){
			
			GoClientMsgListener clientMsgListener = bean.getClass().getAnnotation(GoClientMsgListener.class);
			GoServerMsgListener serverMsgListener = bean.getClass().getAnnotation(GoServerMsgListener.class);
			if(clientMsgListener != null){
				IocContainer.putMsgListener("clientMsgListener", (MsgListener) bean);
			}
			if(serverMsgListener != null){
				IocContainer.putMsgListener("serverMsgListener", (MsgListener) bean);
			}
		}
		
	}
	
	private void scannerGoController(Object bean){
		
		GoController controller = bean.getClass().getAnnotation(GoController.class);
		GoRequestMapping requestMapping = bean.getClass()
				.getAnnotation(GoRequestMapping.class);
		if(controller != null && requestMapping != null){
			String basePath = requestMapping.path();
			
			Method[] methods = bean.getClass().getMethods();
			if(methods != null){
				for(Method method : methods){
					
					GoRequestMapping reqMapping = method
							.getAnnotation(GoRequestMapping.class);
					if(reqMapping == null){
						continue;
					}
					String path = reqMapping.path();
					InvokerHolder invokerHolder = InvokerHolder.valueOf(bean, method);
					String url = (basePath + "/" + path).replaceAll("/+", "/").trim();
					try{
						IocContainer.putInvokerHolder(url, invokerHolder);
					}catch(Exception e){
						LoggerUtils.warn("GoController扫描-{}-{}",e.getMessage()
								,method.getName());
					}
				}
			}
		}
		
	}
	
	

}
