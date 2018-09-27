package com.gosuncn.netty;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gosuncn.netty.core.accepter.DefaultClientMsgAccepter;
import com.gosuncn.netty.core.codec.DefaultDecoder;
import com.gosuncn.netty.core.codec.DefaultEncoder;
import com.gosuncn.netty.core.model.DefaultDTO;
import com.gosuncn.netty.core.model.DefaultRequestHeader;
import com.gosuncn.netty.core.model.MsgTypeEnum;
import com.gosuncn.netty.core.processor.ClientNettyProcessor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoNettyComponentApplicationTests {
	
	private ClientNettyProcessor processor;

	
	@Before
	public void init() throws Exception{
		
		processor = new ClientNettyProcessor("127.0.0.1", 8080);
		List<Class<?>> handlerClazzList = new ArrayList<>();
		handlerClazzList.add(DefaultDecoder.class);
		handlerClazzList.add(DefaultEncoder.class);
		handlerClazzList.add(DefaultClientMsgAccepter.class);
		processor.start(handlerClazzList);
		
	}
	
	@Test
	public void send(){
		
		DefaultRequestHeader requestHeader = new DefaultRequestHeader();
		requestHeader.setRequestType((short)1);
		requestHeader.setUrl("xiaomo/helloworld");
		DefaultDTO dto = new DefaultDTO();
		dto.setMsgType(MsgTypeEnum.REQUEST.getValue());
		dto.setHeader(requestHeader);
		dto.setHeaderLen((short)requestHeader.getBytes().length);
		byte[] body = new byte[10];
		body[0] = 6;
		body[1] = 6;
		body[2] = 6;
		dto.setBodyLen(body.length);
		dto.setBody(body);
		processor.send(dto);
		
	}

}
