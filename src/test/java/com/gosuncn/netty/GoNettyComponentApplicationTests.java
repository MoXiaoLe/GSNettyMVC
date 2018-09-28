package com.gosuncn.netty;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gosuncn.netty.common.util.JsonUtils;
import com.gosuncn.netty.core.accepter.DefaultClientMsgAccepter;
import com.gosuncn.netty.core.codec.DefaultDecoder;
import com.gosuncn.netty.core.codec.DefaultEncoder;
import com.gosuncn.netty.core.model.BodyTypeEnum;
import com.gosuncn.netty.core.model.DefaultDTO;
import com.gosuncn.netty.core.model.DefaultRequestHeader;
import com.gosuncn.netty.core.model.MsgTypeEnum;
import com.gosuncn.netty.core.processor.ClientNettyProcessor;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class GoNettyComponentApplicationTests {
	
	private ClientNettyProcessor processor;

	
	@Before
	public void init() throws Exception{
		
		processor = new ClientNettyProcessor("169.254.204.125", 8080);
		List<Class<?>> handlerClazzList = new ArrayList<>();
		handlerClazzList.add(DefaultDecoder.class);
		handlerClazzList.add(DefaultEncoder.class);
		handlerClazzList.add(DefaultClientMsgAccepter.class);
		processor.start(handlerClazzList);
		
	}
	
	@Test
	public void send(){
		
		DefaultRequestHeader requestHeader = new DefaultRequestHeader();
		requestHeader.setRequestType(BodyTypeEnum.JSON.getValue());
		requestHeader.setUrl("169.254.204.125:8080/xiaomo/hello");
		DefaultDTO dto = new DefaultDTO();
		dto.setMsgType(MsgTypeEnum.REQUEST.getValue());
		dto.setHeader(requestHeader);
		dto.setHeaderLen((short)requestHeader.getBytes().length);
		City city = new City();
		byte[] body = JsonUtils.toJsonString(city).getBytes(Charset.forName("UTF-8"));
		dto.setBodyLen(body.length);
		dto.setBody(body);
		processor.send(dto);
		
		
		System.out.println("结束单元测试");
		
	}
	
	static class City{
		
		private String name = "肇庆";
		private String code = "zhaoqing";
		private double longitude = 102.43;
		private double latitude = 231.90;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
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

}
