package com.jiale.netty.core.model;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年10月9日
 * @description 响应状态码枚举
 */
public enum ResponseStatusCodeEnum {

	NOT_FOND((short)404,"msg=can not find invoker"),
	OK((short)200,"msg=handle success"),
	ERROR((short)500,"msg=handle fail"),
	BAD_REQUEST((short)400,"msg=invalid reqeust");
	
	/**
	 * 状态码
	 */
	private short code;
	/**
	 * 结果描述
	 */
	private String msg;
	
	private ResponseStatusCodeEnum(short code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public short getCode() {
		return code;
	}

	public void setCode(short code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
