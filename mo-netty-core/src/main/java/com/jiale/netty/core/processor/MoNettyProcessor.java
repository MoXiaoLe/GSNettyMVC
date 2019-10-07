package com.jiale.netty.core.processor;

import com.jiale.netty.core.accepter.RequestAccepter;
import com.jiale.netty.core.accepter.ResponseAccepter;
import com.jiale.netty.core.codec.RequestDecoder;
import com.jiale.netty.core.codec.ResponseDecoder;
import com.jiale.netty.core.codec.RequestEncoder;
import com.jiale.netty.core.codec.ResponseEncoder;
import io.netty.channel.ChannelHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class MoNettyProcessor {

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
			handlerClazzList.add(decoder == null ? ResponseDecoder.class : decoder);
			handlerClazzList.add(encoder == null ? RequestEncoder.class : encoder);
			handlerClazzList.add(accepter == null ? ResponseAccepter.class : accepter);
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
