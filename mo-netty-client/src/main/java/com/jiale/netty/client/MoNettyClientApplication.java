package com.jiale.netty.client;

import com.jiale.netty.client.model.DeviceModel;
import com.jiale.netty.core.config.Configuration;
import com.jiale.netty.core.model.CodecConst;
import com.jiale.netty.core.model.RequestDTO;
import com.jiale.netty.client.processor.ClientNettyProcessor;
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

        Configuration configuration = new Configuration();
        configuration.scanPackageName = "com.jiale.netty.client";
        ClientNettyProcessor processor = ClientNettyProcessor.clientBuilder()
                .host("127.0.0.1")
                .port(8089)
                .build();
        processor.start(configuration);

        test1(processor);
        test2(processor);

    }


    private void test1(ClientNettyProcessor processor){

        RequestDTO requestDTO = new RequestDTO();

        requestDTO.startFlag = CodecConst.START_FLAG;
        requestDTO.msgType = 1;
        requestDTO.url = "/test/login".getBytes();
        requestDTO.body = "username=xiaomo&password=123456".getBytes();

        processor.send(requestDTO);

    }

    private void test2(ClientNettyProcessor processor){

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.startFlag = CodecConst.START_FLAG;
        requestDTO.msgType = 2;
        requestDTO.url = "/test/deviceInfo".getBytes();

        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setDeviceId("123321");
        deviceModel.setDeviceName("helloWorld");
        deviceModel.setDeviceProducer("gosuncn");
        deviceModel.setDeviceType(666);
        deviceModel.setLatitude(12);
        deviceModel.setLongitude(23.4);
        requestDTO.body = deviceModel.getBytes();

        processor.send(requestDTO);

    }


}