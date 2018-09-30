package com.gosuncn.netty.business.controller;


import com.gosuncn.netty.business.model.TestModel;
import com.gosuncn.netty.common.util.JsonUtils;
import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.annotation.GoController;
import com.gosuncn.netty.core.annotation.GoRequestMapping;
import com.gosuncn.netty.core.model.GoSession;

@GoController
@GoRequestMapping(path = "/xiaomo/")
public class TestController {

	@GoRequestMapping(path = "hello")
	public TestModel testMsg(TestModel model,GoSession session){
		
		LoggerUtils.info("会话ID-{}",session.getChannelId());
		
		LoggerUtils.info("消息-{}",JsonUtils.toJsonString(model));
		
		model.setDeviceId("666666");
		model.setDeviceType("航天定位卫星");
		
		return model;
		
	}
	
	
	
	
}
