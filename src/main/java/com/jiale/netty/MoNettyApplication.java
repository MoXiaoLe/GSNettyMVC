package com.jiale.netty;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jiale.netty.core.processor.MoNettyProcessor;
import com.jiale.netty.core.processor.MoNettyProcessor.ServerBuilder;

@SpringBootApplication
public class MoNettyApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MoNettyApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// startServer();
		
	}
	
	public void startServer() throws Exception{
		
		ServerBuilder builder =  MoNettyProcessor.serverBuilder();
		builder.port(8089).build().start();
		
	}
	
}
