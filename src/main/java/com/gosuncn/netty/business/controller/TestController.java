package com.gosuncn.netty.business.controller;


import com.gosuncn.netty.business.model.DeviceModel;
import com.gosuncn.netty.business.model.ResponseModel;
import com.gosuncn.netty.common.util.JsonUtils;
import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.annotation.GoController;
import com.gosuncn.netty.core.annotation.GoRequestMapping;
import com.gosuncn.netty.core.model.GoRequest;
import com.gosuncn.netty.core.model.GoSession;

@GoController
@GoRequestMapping(path = "/test/")
public class TestController {
	
	@GoRequestMapping(path = "login")
	public ResponseModel testLogin(GoSession session){
		
		LoggerUtils.info("会话ID-{}",session.getChannelId());
		
		session.setAttribute("login", true);
		ResponseModel responseModel = new ResponseModel();
		responseModel.setMessage("登录成功");
		
		return responseModel;
		
	}

	@GoRequestMapping(path = "helloWithLogin")
	public ResponseModel testMsgAfterLogin(DeviceModel model,GoSession session){
		
		LoggerUtils.info("消息-{}",JsonUtils.toJsonString(model));
		
		ResponseModel responseModel = new ResponseModel();
		Boolean login = (Boolean) session.getAttribute("login");
		if(login != null && login == true){
			model.setDeviceType("航天定位卫星");
			model.setDeviceName("中国北斗系列第七号卫星");
			model.setLatitude(132.68);
			model.setLongitude(326.66);
			responseModel.setModel(model);
			responseModel.setMessage("操作成功");
		}else{
			responseModel.setMessage("未登录，登录后才有权访问");
		}
		
		return responseModel;
		
	}
	
	@GoRequestMapping(path = "helloWithoutLogin")
	public DeviceModel testMsg(String name,String address
			,GoRequest request,GoSession session){
		
		LoggerUtils.info(name);
		LoggerUtils.info(address);
		DeviceModel model = new DeviceModel();
		model.setDeviceType("航天定位卫星");
		model.setDeviceName("中国北斗系列第七号卫星");
		model.setLatitude(132.68);
		model.setLongitude(326.66);
		
		return model;
		
	}
	
}
