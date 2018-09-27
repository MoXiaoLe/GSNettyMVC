package com.gosuncn.netty.core.processor;

import java.util.List;

public interface GoNettyProcessor {

	void start(final List<Class<?>> handlerClazzList) throws Exception;
	
	
}
