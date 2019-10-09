package com.jiale.netty.server.processor;


import com.jiale.netty.core.accepter.RequestAccepter;
import com.jiale.netty.core.codec.RequestDecoder;
import com.jiale.netty.core.codec.ResponseEncoder;
import com.jiale.netty.core.processor.MoNettyProcessor;
import com.jiale.netty.core.util.LoggerUtils;
import com.jiale.netty.core.common.IocContainer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月27日
 * @description 服务端处理入口
 */
public class ServerNettyProcessor extends MoNettyProcessor {

	/**监听端口*/
    private int port;
    /**缓冲池队列大小*/
    private int buffSize;
    /**netty核心服务类*/
    private ServerBootstrap bootstrap = new ServerBootstrap();
    /**boss 线程组*/
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    /** worker线程组*/
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    /**通道处理器*/
    private List<Class<? extends ChannelHandler>> handlerClazzList;
    
	public ServerNettyProcessor(int port, int buffSize,List<Class<? extends ChannelHandler>> handlerClazzList) {
		super();
		this.port = port;
		this.buffSize = buffSize;
		this.handlerClazzList = handlerClazzList;
		IocContainer.initContext(port);
	}

	@Override
	protected void launch() throws Exception {

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


	public static ServerBuilder serverBuilder(){
		return new ServerBuilder();
	}



	public static class ServerBuilder{

		private int port = 8080;;
		private int bufSize = 2048;
		private final List<Class<? extends ChannelHandler>> handlerClazzList
				= new ArrayList<Class<? extends ChannelHandler>>();
		private Class<? extends ChannelHandler> decoder;
		private Class<? extends ChannelHandler> encoder;
		private Class<? extends ChannelHandler> accepter;

		public ServerNettyProcessor build(){

			handlerClazzList.add(decoder == null ? RequestDecoder.class : decoder);
			handlerClazzList.add(encoder == null ? ResponseEncoder.class : encoder);
			handlerClazzList.add(accepter == null ? RequestAccepter.class : accepter);
			ServerNettyProcessor processor = new ServerNettyProcessor(port,bufSize,handlerClazzList);
			return processor;
		}

		public ServerBuilder port(int port){
			this.port = port;
			return this;
		}

		public ServerBuilder bufSize(int bufSize){
			this.bufSize = bufSize;
			return this;
		}

		public ServerBuilder decoder(Class<? extends ChannelHandler> decoder){
			this.decoder = decoder;
			return this;
		}

		public ServerBuilder encoder(Class<? extends ChannelHandler> encoder){
			this.encoder = encoder;
			return this;
		}

		public ServerBuilder accepter(Class<? extends ChannelHandler> accepter){
			this.accepter = accepter;
			return this;
		}

	}
	
	
}
