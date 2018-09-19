package com.gosuncn.netty.common.util;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月19日
 * @description json 转化工具类
 */
public class JsonUtils {
	
	public static String toJsonString(Object obj){
		
		if(obj == null){
			return "";
		}
		
		return JSON.toJSONString(obj);
		
	}
	
	public static Object toJsonObject(String jsonString){
		
		if(StringUtils.isEmpty(jsonString)){
			return null;
		}
		
		return JSON.parse(jsonString);
		
	}

}
