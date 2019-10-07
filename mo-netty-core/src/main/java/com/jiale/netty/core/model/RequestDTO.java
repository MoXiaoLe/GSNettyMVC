package com.jiale.netty.core.model;

import java.util.Arrays;

/**
 * @author mojiale66@163.com
 * @date 2019/10/7
 * @description 请求数据传输对象(minLength:4+1+2+4=11)
 * -------------------------------------------------------------------------
 * 报文开始标志  | 报文类型  | 请求url长度  |报文体长度  | 请求url  |  报文体  |
 * startFlag   |  msgType  | urlLength   | bodyLength|  url     |  body   |
 * int         |  byte     | short       | int       |  byte[]  |  byte[] |
 * -------------------------------------------------------------------------
 */
public class RequestDTO extends Serializer{

    /**
     * 请求报文开始标志
     */
    public int startFlag;

    /**
     * 报文体类型：1-键值对参数  2-serializer参数
     */
    public byte msgType;

    /**
     * 请求url长度
     */
    public short urlLength;

    /**
     * 请求url
     */
    public byte[] url;

    /**
     * 报文体长度
     */
    public int bodyLength;

    /**
     * 报文体
     */
    public byte[] body;


    @Override
    protected void read() {
        this.startFlag = this.readInt();
        this.msgType = this.readByte();
        this.urlLength = this.readShort();
        this.bodyLength = this.readInt();
        this.url = new byte[this.urlLength];
        this.url = this.readBytes(this.url);
        this.body = new byte[this.bodyLength];
        this.body = this.readBytes(this.body);
    }

    @Override
    protected void write() {
        this.writeInt(this.startFlag);
        this.writeByte(this.msgType);
        this.writeShort(this.urlLength);
        this.writeInt(this.bodyLength);
        this.writeBytes(this.url);
        this.writeBytes(this.body);
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "startFlag=" + startFlag +
                ", msgType=" + msgType +
                ", urlLength=" + urlLength +
                ", url=" + Arrays.toString(url) +
                ", bodyLength=" + bodyLength +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
