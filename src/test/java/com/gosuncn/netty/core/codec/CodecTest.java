package com.gosuncn.netty.core.codec;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gosuncn.netty.common.util.JsonUtils;
import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.common.BufferFactory;
import com.gosuncn.netty.core.model.DefaultDTO;
import com.gosuncn.netty.core.model.DefaultResponseHeader;
import com.gosuncn.netty.core.model.MsgTypeInface;

import io.netty.buffer.ByteBuf;

public class CodecTest {

	private DefaultDecoder decoder;
	
	private DefaultEncoder encoder;
	
	private DefaultDTO defaultDTO;
	
	private DefaultResponseHeader responseHeader;
	
	@Before
	public void init(){
		
		responseHeader = new DefaultResponseHeader();
		responseHeader.setResponseType((byte)1);
		responseHeader.setStatus((byte)2);
		
		defaultDTO = new DefaultDTO();
		defaultDTO.setMsgType(MsgTypeInface.RESPONSE);
		defaultDTO.setHeaderLen((short)responseHeader.getBytes().length);
		defaultDTO.setHeader(responseHeader);
		
		byte[] body = new byte[10];
		body[0] = 6;
		body[1] = 6;
		body[2] = 6;
		defaultDTO.setBodyLen(body.length);
		defaultDTO.setBody(body);
		
		decoder = new DefaultDecoder();
		encoder = new DefaultEncoder();
		
	}
	
	@Test
	public void test() throws Exception{
		
		ByteBuf buff = BufferFactory.buildBuff();
		encoder.encode(null, defaultDTO, buff);
		encoder.encode(null, defaultDTO, buff);
		LoggerUtils.info("编码结果-{}",buff.toString());
		List<Object> resultList = new ArrayList<Object>();
		decoder.decode(null, buff, resultList);
		LoggerUtils.info("解码结果-{}",JsonUtils.toJsonString(resultList));
	}
	

}
