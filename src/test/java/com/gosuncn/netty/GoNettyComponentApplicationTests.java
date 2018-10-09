package com.gosuncn.netty;


import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.gosuncn.netty.core.model.BodyTypeInface;
import com.gosuncn.netty.core.model.DefaultDTO;
import com.gosuncn.netty.core.model.DefaultHeader;
import com.gosuncn.netty.core.model.MsgTypeInface;
import com.gosuncn.netty.core.processor.ClientNettyProcessor;
import com.gosuncn.netty.core.processor.GoNettyProcessor;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GoNettyComponentApplicationTests {
	
	private ClientNettyProcessor processor;

	
	@Before
	public void init() throws Exception{
		
		processor = GoNettyProcessor.clientBuilder()
						.host("127.0.0.1")
						.port(8080)
						.build();
		processor.start();
		
	}
	
	@Test
	public void test(){
		
		getDeviceInfoWithLogin();
		getDeviceInfoWithoutLogin();
		login();
		getDeviceInfoWithLogin();
		getDeviceInfoWithoutLogin();
		
		pause();
		
	}
	
	
	public void login(){
		
		DefaultHeader requestHeader = DefaultHeader.requestHeaderBuilder()
				.url("test/login")
				.build();
		
		DefaultDTO dto = DefaultDTO.buidler()
				.msgType(MsgTypeInface.REQUEST)
				.header(requestHeader)
				.build();
		
		processor.send(dto);
		
		
	}
	
	public void getDeviceInfoWithLogin(){
		
		ParamsModel model = new ParamsModel();
		model.setDeviceId("666666");
		
		DefaultHeader requestHeader = DefaultHeader.requestHeaderBuilder()
				.url("test/helloWithLogin")
				.build();
		
		DefaultDTO dto = DefaultDTO.buidler()
				.msgType(MsgTypeInface.REQUEST)
				.header(requestHeader)
				.body(model)
				.build();
		
		processor.send(dto);
		
	}
	
	public void getDeviceInfoWithoutLogin(){
		
		ParamsModel model = new ParamsModel();
		model.setDeviceId("666666");
		
		DefaultHeader requestHeader = DefaultHeader.requestHeaderBuilder()
				.url("test/helloWithoutLogin")
				.requestType(BodyTypeInface.FORM)
				.build();
		
		DefaultDTO dto = DefaultDTO.buidler()
				.msgType(MsgTypeInface.REQUEST)
				.header(requestHeader)
				.keyValue("name", "xiaomo")
				.keyValue("address", "zhaoqing")
				.build();
		
		processor.send(dto);
		
	}
	
	class ParamsModel{
		
		private String deviceId;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		
		
	}
	
	public void pause(){
		
		try{
			Thread.sleep(5000);
		}catch(Exception e){
		}
		
		System.out.println("输入 stop 结束客户端");
		Scanner scanner = new Scanner(System.in);
		String str = scanner.nextLine();
		while(!"stop".equals(str)){
			System.out.println("输入 stop 结束客户端");
			str = scanner.nextLine();
		}
		scanner.close();
	}
	

}
