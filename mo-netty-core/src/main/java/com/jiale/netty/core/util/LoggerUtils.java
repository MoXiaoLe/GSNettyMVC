/**
 *
 */
package com.jiale.netty.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;


/**
 * @author: mojiale66@163.com
 * @date: 2018年7月2日
 * @description: 日志工具类
 */
public class LoggerUtils {

    private static final String APP_NAME = "MoNetty";

    private static final Logger LOGGER = LoggerFactory.getLogger(APP_NAME);

    private static final ConcurrentHashMap<Class,Logger> LOGGER_CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();

    /**
     * 创建一个logger并返回
     * @param clazz
     * @return
     */
    public static Logger newLogger(Class<?> clazz){
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * 取缓存logger并返回，没有则创建一个
     * @param clazz
     * @return
     */
    public static Logger cacheLogger(Class<?> clazz){
        Logger logger = LOGGER_CONCURRENT_HASH_MAP.get(clazz);
        if(logger != null){
            return logger;
        }
        logger = newLogger(clazz);
        LOGGER_CONCURRENT_HASH_MAP.put(clazz,logger);
        return logger;
    }

    public static void trace(String paramString) {
        LOGGER.trace(paramString);
    }

    public static void trace(String paramString, Object... replaceArgs) {
        LOGGER.trace(paramString, replaceArgs);
    }

    public static void trace(String paramString, Throwable paramThrowable) {
        LOGGER.trace(paramString, paramThrowable);
    }

    public static void debug(String paramString) {
        LOGGER.debug(paramString);
    }

    public static void debug(String paramString, Object... replaceArgs) {
        LOGGER.debug(paramString, replaceArgs);
    }

    public static void debug(String paramString, Throwable paramThrowable) {
        LOGGER.debug(paramString, paramThrowable);
    }

    public static void info(String paramString) {
        LOGGER.info(paramString);
    }

    public static void info(String paramString, Object... replaceArgs) {
        LOGGER.info(paramString, replaceArgs);
    }

    public static void info(String paramString, Throwable paramThrowable) {
        LOGGER.info(paramString, paramThrowable);
    }

    public static void warn(String paramString) {
        LOGGER.warn(paramString);
    }

    public static void warn(String paramString, Object... replaceArgs) {
        LOGGER.warn(paramString, replaceArgs);
    }

    public static void warn(String paramString, Throwable paramThrowable) {
        LOGGER.warn(paramString, paramThrowable);
    }

    public static void error(String paramString) {
        LOGGER.error(paramString);
    }

    public static void error(String paramString, Object... replaceArgs) {
        LOGGER.error(paramString, replaceArgs);
    }

    public static void error(String paramString, Throwable paramThrowable) {
        LOGGER.error(paramString, paramThrowable);
    }

}
