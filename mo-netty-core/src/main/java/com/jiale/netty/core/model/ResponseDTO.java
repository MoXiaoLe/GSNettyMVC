package com.jiale.netty.core.model;

import java.util.Arrays;

/**
 * @author mojiale66@163.com
 * @date 2019/10/7
 * @description 响应数据传输对象(minLength:4+1+2+4=11)
 * ---------------------------------------------------------------
 * 报文开始标志  | 报文类型  | 响应状态码  |  报文体长度  |  报文体  |
 * startFlag   |  msgType  | statusCode |  bodyLength |  body   |
 * int         |  byte     | short      |  int        |  byte[] |
 * --------------------------------------------------------------
 */
public class ResponseDTO extends Serializer{

    /**
     * 报文开始标志
     */
    public int startFlag;

    /**
     *  报文体类型：1-键值对参数  2-serializer参数
     */
    public byte msgType;

    /**
     * 响应状态码
     */
    public short statusCode;

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
        this.statusCode = this.readShort();
        this.bodyLength = this.readInt();
        this.body = new byte[this.bodyLength];
        this.body = this.readBytes(this.body);
    }

    @Override
    protected void write() {
        this.writeInt(this.startFlag);
        this.writeByte(this.msgType);
        this.writeShort(this.statusCode);
        this.writeInt(this.bodyLength);
        this.writeBytes(this.body);
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "startFlag=" + startFlag +
                ", msgType=" + msgType +
                ", statusCode=" + statusCode +
                ", bodyLength=" + bodyLength +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
