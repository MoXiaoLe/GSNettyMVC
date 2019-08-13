package com.jiale.netty.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月28日
 * @description 请求参数
 */ 

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MoRequestParam {

	String key() default "";
	
}
