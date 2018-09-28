package com.gosuncn.netty.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月28日
 * @description 请求路径
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GoRequestMapping {
	
	/**
	 * 请求路径
	 * @return 
	 */
	String path() default "";
}
