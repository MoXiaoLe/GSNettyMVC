package com.gosuncn.netty;


import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.gosuncn.netty.core.model.DefaultDTO;
import com.gosuncn.netty.core.model.DefaultHeader;
import com.gosuncn.netty.core.model.MsgTypeEnum;
import com.gosuncn.netty.core.processor.ClientNettyProcessor;
import com.gosuncn.netty.core.processor.GoNettyProcessor;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GoNettyComponentApplicationTests {
	
	private ClientNettyProcessor processor;

	
	@Before
	public void init() throws Exception{
		
		processor = GoNettyProcessor.clientBuilder()
						.host("169.254.204.125")
						.port(8080)
						.build();
		processor.start();
		
	}
	
	@Test
	public void send(){
		
		ParamsModel model = new ParamsModel();
		model.setDeviceName("中国北斗系列第七号卫星");
		model.setLatitude(132.68);
		model.setLongitude(326.66);
		
		DefaultHeader requestHeader = DefaultHeader.requestHeaderBuilder()
				.url("xiaomo/hello")
				.build();
		
		DefaultDTO dto = DefaultDTO.buidler()
				.msgType(MsgTypeEnum.REQUEST.getValue())
				.header(requestHeader)
				.body(model)
				.build();
		
		processor.send(dto);
		
		
		pause();
		
	}
	
	class ParamsModel{
		
		private String deviceName;
		private double longitude;
		private double latitude;
		public String getDeviceName() {
			return deviceName;
		}
		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
		}
		public double getLongitude() {
			return longitude;
		}
		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
		public double getLatitude() {
			return latitude;
		}
		public void setLatitude(double latitude) {
			this.latitude = latitude;
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
