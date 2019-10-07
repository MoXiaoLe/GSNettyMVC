package com.jiale.netty.core.annotation;

import java.lang.annotation.*;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月28日
 * @description 请求路径
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MoRequestMapping {
	
	/**
	 * 请求路径
	 * @return 
	 */
	String path() default "";
}
