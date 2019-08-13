package com.jiale.netty;

import java.util.Scanner;

import com.jiale.netty.core.processor.ClientNettyProcessor;
import com.jiale.netty.core.processor.MoNettyProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jiale.netty.core.model.BodyTypeInface;
import com.jiale.netty.core.model.DefaultDTO;
import com.jiale.netty.core.model.DefaultHeader;
import com.jiale.netty.core.model.MsgTypeInface;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientTest {
	
	private ClientNettyProcessor processor;

	
	@Before
	public void init() throws Exception{
		
		processor = MoNettyProcessor.clientBuilder()
						.host("127.0.0.1")
						.port(8089)
						.build();
		processor.start();
		
	}
	
	@Test
	public void test(){
		
		getDeviceInfo();
		login();
		getDeviceInfo();
		pause();
		
	}
	
	
	public void login(){
		
		DefaultHeader requestHeader = DefaultHeader.requestHeaderBuilder()
				.requestType(BodyTypeInface.FORM)
				.url("test/login")
				.build();
		
		DefaultDTO dto = DefaultDTO.buidler()
				.msgType(MsgTypeInface.REQUEST)
				.header(requestHeader)
				.keyValue("username", "xiaomo")
				.keyValue("password", "123456")
				.build();
		
		processor.send(dto);
		
	}
	
	public void getDeviceInfo(){
		
		ParamsModel model = new ParamsModel();
		model.setDeviceProducer("高新兴科技");
		model.setDeviceType(123);
		
		DefaultHeader requestHeader = DefaultHeader.requestHeaderBuilder()
				.url("test/deviceInfo")
				.requestType(BodyTypeInface.JSON)
				.build();
		
		DefaultDTO dto = DefaultDTO.buidler()
				.msgType(MsgTypeInface.REQUEST)
				.header(requestHeader)
				.body(model)
				.build();
		
		processor.send(dto);
		
	}
	
	class ParamsModel{
		
		private String deviceProducer;
		private int deviceType;

		public String getDeviceProducer() {
			return deviceProducer;
		}

		public void setDeviceProducer(String deviceProducer) {
			this.deviceProducer = deviceProducer;
		}

		public int getDeviceType() {
			return deviceType;
		}

		public void setDeviceType(int deviceType) {
			this.deviceType = deviceType;
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
