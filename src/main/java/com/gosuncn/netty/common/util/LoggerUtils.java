/**
 * 
 */
package com.gosuncn.netty.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author: mojiale66@163.com
 * @date:   2018年7月2日
 * @description:  日志工具类
 */
public class LoggerUtils {
	
		public static Logger logger = LoggerFactory.getLogger("GoNettyComponent");
		
		public static void trace(String paramString) {
			logger.trace(paramString);
		}
		
		public static void trace(String paramString, Object... replaceArgs) {
			logger.trace(paramString, replaceArgs);
		}

		public static void trace(String paramString, Throwable paramThrowable) {
			logger.trace(paramString, paramThrowable);
		}

		public static void debug(String paramString) {
			logger.debug(paramString);
		}
		
		public static void debug(String paramString, Object... replaceArgs) {
			logger.debug(paramString, replaceArgs);
		}
	
		public static void debug(String paramString, Throwable paramThrowable) {
			logger.debug(paramString, paramThrowable);
		}
		
		public static void info(String paramString) {
			logger.info(paramString);
		}
		
		public static void info(String paramString, Object... replaceArgs) {
			logger.info(paramString, replaceArgs);
		}

		public static void info(String paramString, Throwable paramThrowable) {
			logger.info(paramString, paramThrowable);
		}

		public static void warn(String paramString) {
			logger.warn(paramString);
		}
		
		public static void warn(String paramString, Object... replaceArgs) {
			logger.warn(paramString, replaceArgs);
		}
		
		public static void warn(String paramString, Throwable paramThrowable) {
			logger.warn(paramString, paramThrowable);
		}

		public static void error(String paramString) {
			logger.error(paramString);
		}
		
		public static void error(String paramString, Object... replaceArgs) {
			logger.error(paramString, replaceArgs);
		}

		public static void error(String paramString, Throwable paramThrowable) {
			logger.error(paramString, paramThrowable);
		}

}
