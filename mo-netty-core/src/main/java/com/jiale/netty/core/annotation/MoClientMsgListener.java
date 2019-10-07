package com.jiale.netty.core.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年10月9日
 * @description 客户端接收消息回调
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MoClientMsgListener {

}
