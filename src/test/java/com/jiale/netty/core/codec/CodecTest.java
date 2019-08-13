package com.jiale.netty.core.codec;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jiale.netty.common.util.JsonUtils;
import com.jiale.netty.common.util.LoggerUtils;
import org.junit.Before;
import org.junit.Test;

import com.jiale.netty.core.common.BufferFactory;
import com.jiale.netty.core.model.DefaultDTO;
import com.jiale.netty.core.model.DefaultResponseHeader;
import com.jiale.netty.core.model.MsgTypeInface;

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
		
		Class c = Collection.class;
		System.out.println(c.isAssignableFrom(List.class));
		
		List<String> aa = new ArrayList<String>();
		
		ParameterizedType type = (ParameterizedType)aa.getClass().getGenericSuperclass();
		Class<?> genericClazz = null; 
		if(type != null){
			Type[] types = type.getActualTypeArguments();
			if(types != null && types.length > 0){
				genericClazz = types[0].getClass();
				System.out.println(genericClazz.getSimpleName());
			}
		}
		
		
		
		ByteBuf buff = BufferFactory.buildBuff();
		encoder.encode(null, defaultDTO, buff);
		encoder.encode(null, defaultDTO, buff);
		LoggerUtils.info("编码结果-{}",buff.toString());
		List<Object> resultList = new ArrayList<Object>();
		decoder.decode(null, buff, resultList);
		LoggerUtils.info("解码结果-{}", JsonUtils.toJsonString(resultList));
	}
	

}
