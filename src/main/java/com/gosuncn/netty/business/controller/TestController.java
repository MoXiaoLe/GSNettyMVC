package com.gosuncn.netty.business.controller;



import com.gosuncn.netty.business.model.DeviceModel;
import com.gosuncn.netty.business.model.ResponseModel;
import com.gosuncn.netty.common.util.JsonUtils;
import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.annotation.GoController;
import com.gosuncn.netty.core.annotation.GoRequestMapping;
import com.gosuncn.netty.core.model.GSSession;

@GoController
@GoRequestMapping(path = "/test/")
public class TestController {
	
	@GoRequestMapping(path = "login")
	public ResponseModel testLogin(String username,String password,GSSession session){

		LoggerUtils.info("用户登录-{}-{}",username,password);
		// ignore login logic ...
		session.setAttribute("login", true);
		ResponseModel responseModel = new ResponseModel();
		responseModel.setMessage("登录成功");
		
		return responseModel;
		
	}
	
	
	@GoRequestMapping(path = "deviceInfo")
	public ResponseModel testGetDeviceInfo(GSSession session,DeviceModel paramsModel){

		LoggerUtils.info("接受到请求参数：-{}",JsonUtils.toJsonString(paramsModel));
		
		ResponseModel responseModel = new ResponseModel();
		DeviceModel model = null;
	
		Boolean loginFlag = (Boolean) session.getAttribute("login");
		if(loginFlag != null && loginFlag){
			model = new DeviceModel();
			model.setDeviceId("666");
			model.setLatitude(123.43);
			model.setLongitude(234.56);
			model.setDeviceName("gps定位设备");
			model.setDeviceType(10001);
			responseModel.setModel(model);
		}else{
			responseModel.setMessage("访问设备信息需要登录授权");
		}
		return responseModel;
		
	}
	
}
