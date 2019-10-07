package com.jiale.netty.core.common;

import com.jiale.netty.core.util.LoggerUtils;
import com.jiale.netty.core.accepter.MsgListener;
import com.jiale.netty.core.annotation.MoClientMsgListener;
import com.jiale.netty.core.annotation.MoController;
import com.jiale.netty.core.annotation.MoRequestMapping;
import com.jiale.netty.core.annotation.MoServerMsgListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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
		
		// 扫描 MoClientMsgListener 、 MoServerMsgListener 注解
		scannerMsgListener(bean);
		
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}
	
	
	private void scannerMsgListener(Object bean){
		
		if(bean instanceof MsgListener){
			
			MoClientMsgListener clientMsgListener = bean.getClass().getAnnotation(MoClientMsgListener.class);
			MoServerMsgListener serverMsgListener = bean.getClass().getAnnotation(MoServerMsgListener.class);
			if(clientMsgListener != null){
				IocContainer.putMsgListener("clientMsgListener", (MsgListener) bean);
			}
			if(serverMsgListener != null){
				IocContainer.putMsgListener("serverMsgListener", (MsgListener) bean);
			}
		}
		
	}
	
	private void scannerGoController(Object bean){
		
		MoController controller = bean.getClass().getAnnotation(MoController.class);
		MoRequestMapping requestMapping = bean.getClass()
				.getAnnotation(MoRequestMapping.class);
		if(controller != null && requestMapping != null){
			String basePath = requestMapping.path();
			
			Method[] methods = bean.getClass().getMethods();
			if(methods != null){
				for(Method method : methods){
					
					MoRequestMapping reqMapping = method
							.getAnnotation(MoRequestMapping.class);
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
