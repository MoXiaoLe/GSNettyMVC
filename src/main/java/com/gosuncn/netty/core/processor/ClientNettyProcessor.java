package com.gosuncn.netty.core.processor;

import java.net.InetSocketAddress;
import java.util.List;

import com.gosuncn.netty.common.util.LoggerUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 客户端处理入口
 */
public class ClientNettyProcessor implements GoNettyProcessor{

	/**服务机IP*/
    private String host;
    /**服务机端口*/
    private int port;
    /**netty核心服务类*/
    private Bootstrap bootstrap = new Bootstrap();
    /**会话通道*/
    private Channel channel;
    /**线程组*/
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    
    public ClientNettyProcessor(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	@Override
	public void start(final List<Class<?>> handlerClazzList) throws Exception {
		
		LoggerUtils.info("启动客户端");
		// 设置循环线程组
        bootstrap.group(workerGroup);
        // 设置channel工厂
        bootstrap.channel(NioSocketChannel.class);
        // 设置管道
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
            	if(handlerClazzList != null){
					for(Class<?> clazz : handlerClazzList){
						ch.pipeline().addLast((ChannelHandler)clazz.newInstance());
					}
				}
            }
        });
        // 连接服务机
        connect();
        LoggerUtils.info("客户端启动完成，远程主机-{}:{}",host,port);
	}

	public void connect() throws InterruptedException{
        LoggerUtils.info("客户端连接远程主机-host:{},port:{}",host,port);
        ChannelFuture connect = bootstrap.connect(new InetSocketAddress(host, port));
        connect.sync();
        channel = connect.channel();
    }

    public void shutdown() {
    	channel.close();
        workerGroup.shutdownGracefully();
    }

    public void send(Object message){
        if(channel == null || !channel.isActive()){
            try {
                connect();
            }catch (InterruptedException e){
                LoggerUtils.warn("连接远程主机失败，失败原因-{}",e.getMessage(),e);
            }
        }
        channel.writeAndFlush(message);
    }
}
