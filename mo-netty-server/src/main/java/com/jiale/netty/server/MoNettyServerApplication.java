package com.jiale.netty.server;


import com.jiale.netty.core.config.Configuration;
import com.jiale.netty.server.processor.ServerNettyProcessor;
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

		Configuration configuration = new Configuration();
		configuration.scanPackageName = "com.jiale.netty.server";
		ServerNettyProcessor.ServerBuilder builder =  ServerNettyProcessor.serverBuilder();
		builder.port(8089).build().start(configuration);
		
	}
	
}
