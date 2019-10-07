package com.jiale.netty.client;

import com.jiale.netty.core.model.CodecConst;
import com.jiale.netty.core.model.RequestDTO;
import com.jiale.netty.core.processor.ClientNettyProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mojiale66@163.com
 * @date 2019/10/7
 * @description
 */
@SpringBootApplication
public class MoNettyClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MoNettyClientApplication.class);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {


        ClientNettyProcessor processor = ClientNettyProcessor.clientBuilder()
                .host("127.0.0.1")
                .port(8089)
                .build();
        processor.start();


        RequestDTO requestDTO = new RequestDTO();

        requestDTO.startFlag = CodecConst.START_FLAG;
        requestDTO.msgType = 1;
        requestDTO.url = "asdjkdasj;".getBytes();
        requestDTO.body = "adssdasda".getBytes();

        processor.send(requestDTO);

    }



}