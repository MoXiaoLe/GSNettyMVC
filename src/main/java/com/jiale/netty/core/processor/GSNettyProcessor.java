package com.jiale.netty.core.processor;

import java.util.ArrayList;
import java.util.List;

import com.jiale.netty.core.accepter.DefaultClientMsgAccepter;
import com.jiale.netty.core.accepter.DefaultServerMsgAccepter;
import com.jiale.netty.core.codec.DefaultDecoder;
import com.jiale.netty.core.codec.DefaultEncoder;

import io.netty.channel.ChannelHandler;

public abstract class GSNettyProcessor {

	public abstract void start() throws Exception;
	
	public static ClientBuilder clientBuilder(){
		return new ClientBuilder();
	}
	
	public static ServerBuilder serverBuilder(){
		return new ServerBuilder();
	}
	
	
	public static class ClientBuilder{
		
		private int port;
		private String host;
		private final List<Class<? extends ChannelHandler>> handlerClazzList
			= new ArrayList<Class<? extends ChannelHandler>>();;
		private Class<? extends ChannelHandler> decoder;
		private Class<? extends ChannelHandler> encoder;
		private Class<? extends ChannelHandler> accepter;
		
		public ClientNettyProcessor build(){
			handlerClazzList.add(decoder == null ? DefaultDecoder.class : decoder);
			handlerClazzList.add(encoder == null ? DefaultEncoder.class : encoder);
			handlerClazzList.add(accepter == null ? DefaultClientMsgAccepter.class : accepter);
			ClientNettyProcessor processor = new ClientNettyProcessor(host, port,handlerClazzList);
			return processor;
		}
		
		public ClientBuilder port(int port){
			this.port = port;
			return this;
		}
		
		public ClientBuilder host(String host){
			this.host = host;
			return this;
		}
		
		public ClientBuilder decoder(Class<? extends ChannelHandler> decoder){
			this.decoder = decoder;
			return this;
		}
		
		public ClientBuilder encoder(Class<? extends ChannelHandler> encoder){
			this.encoder = encoder;
			return this;
		}
		
		public ClientBuilder accepter(Class<? extends ChannelHandler> accepter){
			this.accepter = accepter;
			return this;
		}
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
			
			handlerClazzList.add(decoder == null ? DefaultDecoder.class : decoder);
			handlerClazzList.add(encoder == null ? DefaultEncoder.class : encoder);
			handlerClazzList.add(accepter == null ? DefaultServerMsgAccepter.class : accepter);
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
