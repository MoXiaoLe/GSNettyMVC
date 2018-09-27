package com.gosuncn.netty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gosuncn.netty.core.accepter.DefaultServerMsgAccepter;
import com.gosuncn.netty.core.codec.DefaultDecoder;
import com.gosuncn.netty.core.codec.DefaultEncoder;
import com.gosuncn.netty.core.processor.ServerNettyProcessor;

@SpringBootApplication
public class GoNettyComponentApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(GoNettyComponentApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		startServer();
		
	}
	
	public void startServer() throws Exception{
		
		ServerNettyProcessor processor = new ServerNettyProcessor();
		List<Class<?>> handlerClazzList = new ArrayList<>();
		handlerClazzList.add(DefaultDecoder.class);
		handlerClazzList.add(DefaultEncoder.class);
		handlerClazzList.add(DefaultServerMsgAccepter.class);
		processor.start(handlerClazzList);
		
	}
	
}
