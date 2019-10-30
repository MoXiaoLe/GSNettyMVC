package com.jiale.netty.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author mojiale@bluemoon.com.cn
 * @date 2019/10/9
 * @description 文件操作工具类
 */
public class FileUtils {

    /**
     * 以追加形式从fromIndex位置写入文件
     * @param file
     * @param fromIndex
     * @param data
     * @throws IOException
     */
    public static void writeToFile(File file,long fromIndex,byte[] data) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rws");
        fromIndex = randomAccessFile.length() > fromIndex ? fromIndex : randomAccessFile.length();
        fromIndex = fromIndex < 0 ? 0 : fromIndex;
        randomAccessFile.skipBytes((int)fromIndex);
        randomAccessFile.write(data,0,data.length);
        randomAccessFile.close();
    }

}
