package com.jiale.netty.server.controller;

import com.jiale.netty.core.annotation.MoController;
import com.jiale.netty.core.annotation.MoRequestMapping;
import com.jiale.netty.core.util.FileUtils;
import com.jiale.netty.server.model.FileModel;
import com.jiale.netty.server.model.ResponseModel;

import java.io.File;

/**
 * @author mojiale@bluemoon.com.cn
 * @date 2019/10/9
 * @description 测试传输文件
 */
@MoController
@MoRequestMapping(path = "/file")
public class FileController {

    @MoRequestMapping(path = "/transfer")
    public ResponseModel testTransferFile(FileModel fileModel){

        ResponseModel responseModel = new ResponseModel();
        String path = "C:\\Users\\Administrator\\Desktop\\fileTest";
        String filename = fileModel.getFilename();
        File file = new File(path,filename);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileUtils.writeToFile(file,fileModel.getCurrentIndex(),fileModel.getData());
            responseModel.setMessage("传输文件成功");
        }catch (Exception e){
            e.printStackTrace();
            responseModel.setMessage(e.getMessage());
        }
        return responseModel;
    }
}
