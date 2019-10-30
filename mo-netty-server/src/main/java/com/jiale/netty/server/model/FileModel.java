package com.jiale.netty.server.model;

import com.jiale.netty.core.model.Serializer;

/**
 * @author mojiale@bluemoon.com.cn
 * @date 2019/10/9
 * @description
 */
public class FileModel extends Serializer {

    private String filename;

    private long fileLength;

    private int currentIndex;

    private int currentLength;

    private byte[] data;

    @Override
    protected void read() {
        this.filename = this.readString();
        this.fileLength = this.readLong();
        this.currentLength = this.readInt();
        this.currentIndex = this.readInt();
        this.data = new byte[this.currentLength];
        this.data = this.readBytes(this.data);
    }

    @Override
    protected void write() {
        this.writeString(this.filename);
        this.writeLong(this.fileLength);
        this.writeInt(this.currentLength);
        this.writeInt(this.currentIndex);
        this.writeBytes(this.data);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
