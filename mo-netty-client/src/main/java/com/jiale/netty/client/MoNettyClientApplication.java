package com.jiale.netty.client;

import com.jiale.netty.client.model.DeviceModel;
import com.jiale.netty.client.model.FileModel;
import com.jiale.netty.core.config.Configuration;
import com.jiale.netty.core.model.CodecConst;
import com.jiale.netty.core.model.RequestDTO;
import com.jiale.netty.client.processor.ClientNettyProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

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
        testFile(processor);

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


    private void testFile(ClientNettyProcessor processor) throws IOException {

        /**
         * 1.计算文件大小
         * 2.分割发送次数
         * 3.读取区间byte[]
         * 4.发送数据
         */
        // 每次发送1M
        int sendPerLength = 1024*1024;
        // 已发送长度
        long sentLength = 0;
        String path = "C:\\Users\\Administrator\\Desktop\\test\\mapper";
        String filename = "测试.mp4";
        File file = new File(path,filename);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
        long fileLength = file.length();
        while (sentLength < fileLength){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            randomAccessFile.seek((int)sentLength);
            //randomAccessFile.skipBytes((int)sentLength);
            byte[] data = new byte[4096];
            int length;
            int tempLength = 0;
            while (tempLength < sendPerLength
                    && (length = randomAccessFile.read(data)) != -1){
                byteArrayOutputStream.write(data,0,length);
                tempLength += length;
            }
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.startFlag = CodecConst.START_FLAG;
            requestDTO.msgType = 2;
            requestDTO.url = "/file/transfer".getBytes();
            FileModel fileModel = new FileModel();
            fileModel.setFilename(filename);
            fileModel.setFileLength(fileLength);
            fileModel.setCurrentIndex((int)sentLength);
            byte[] fileBytes = byteArrayOutputStream.toByteArray();
            fileModel.setCurrentLength(fileBytes.length);
            fileModel.setData(fileBytes);
            requestDTO.body = fileModel.getBytes();
            processor.send(requestDTO);
            sentLength += fileBytes.length;
            byteArrayOutputStream.close();
        }
        randomAccessFile.close();
    }
}