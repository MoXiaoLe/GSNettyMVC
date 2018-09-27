package com.gosuncn.netty.core.processor;



import java.util.List;

import com.gosuncn.netty.common.util.LoggerUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 服务端处理入口
 */
public class ServerNettyProcessor implements GoNettyProcessor{

	/**监听端口*/
    private int port = 8080;
    /**缓冲池队列大小*/
    private int buffSize = 2048;
    /**netty核心服务类*/
    private ServerBootstrap bootstrap = new ServerBootstrap();
    /**boss 线程组*/
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    /** worker线程组*/
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    
	public ServerNettyProcessor() {
		super();
	}
	
	public ServerNettyProcessor(int port, int buffSize) {
		super();
		this.port = port;
		this.buffSize = buffSize;
	}

	@Override
	public void start(final List<Class<?>> handlerClazzList) throws Exception {

		LoggerUtils.info("开始启动服务器");
		try {
			// 设置循环线程组
			bootstrap.group(bossGroup, workerGroup);
			// 设置channel工厂
			bootstrap.channel(NioServerSocketChannel.class);
			// 设置管道
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					if(handlerClazzList != null){
						for(Class<?> clazz : handlerClazzList){
							ch.pipeline().addLast((ChannelHandler)clazz.newInstance());
						}
					}
				}
			});
			// 链接缓冲池队列大小
			bootstrap.option(ChannelOption.SO_BACKLOG, buffSize);
			// 绑定端口
			bootstrap.bind(port).sync();
			LoggerUtils.info("服务器启动完成，监听端口-{}",port);
		} catch (Exception e) {
			LoggerUtils.warn("服务器启动失败-{}",e.getMessage());
			throw e;
		}

	}
	
	
}
