package com.gosuncn.netty;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gosuncn.netty.core.processor.GSNettyProcessor;
import com.gosuncn.netty.core.processor.GSNettyProcessor.ServerBuilder;

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
		
		ServerBuilder builder =  GSNettyProcessor.serverBuilder();
		builder.build().start();
		
	}
	
}
