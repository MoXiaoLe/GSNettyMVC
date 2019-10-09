package com.jiale.netty.core.model;

import java.nio.charset.Charset;

/**
 *
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 系统常量集合
 */
public interface SystemConst {


	/**
	 * body以基本类型形式解编码
	 */
	byte SERIALIZER = (byte)2;
	
	/**
	 * body以表单形式解编码
	 */
	byte FORM = (byte)1;

	/**
	 * 字符集编码
	 */
	Charset CHARSET = Charset.forName("UTF-8");

	/**
	 * java类型
	 */
	String FILE = "file";

	/**
	 * jar类型
	 */
	String JAR = "jar";

	/**
	 * class类型
	 */
	String CLASS = "class";



	
}
