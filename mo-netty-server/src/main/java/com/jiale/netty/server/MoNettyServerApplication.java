package com.jiale.netty.server;


import com.jiale.netty.core.processor.MoNettyProcessor;
import com.jiale.netty.core.processor.MoNettyProcessor.ServerBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoNettyServerApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MoNettyServerApplication.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		 startServer();
		
	}
	
	public void startServer() throws Exception{
		
		ServerBuilder builder =  MoNettyProcessor.serverBuilder();
		builder.port(8089).build().start();
		
	}
	
}
