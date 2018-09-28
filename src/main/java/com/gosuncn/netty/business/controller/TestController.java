package com.gosuncn.netty.business.controller;

import com.gosuncn.netty.common.util.JsonUtils;
import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.annotation.GoController;
import com.gosuncn.netty.core.annotation.GoRequestMapping;
import com.gosuncn.netty.core.common.GoRequest;

@GoController
@GoRequestMapping(path = "/xiaomo/")
public class TestController {

	@GoRequestMapping(path = "hello")
	public void testMsg(GoRequest request){
		
		LoggerUtils.info("接收到消息-{}",JsonUtils.toJsonString(request));
		
	}
	
	
}
