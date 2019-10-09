package com.jiale.netty.core.processor;

import com.jiale.netty.core.accepter.RequestAccepter;
import com.jiale.netty.core.accepter.ResponseAccepter;
import com.jiale.netty.core.codec.RequestDecoder;
import com.jiale.netty.core.codec.ResponseDecoder;
import com.jiale.netty.core.codec.RequestEncoder;
import com.jiale.netty.core.codec.ResponseEncoder;
import com.jiale.netty.core.config.Configuration;
import com.jiale.netty.core.scanner.BusinessScanner;
import com.jiale.netty.core.scanner.PackageScanner;
import io.netty.channel.ChannelHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class MoNettyProcessor {

	/**
	 * 程序启动逻辑实现
	 * @throws Exception
	 */
	protected abstract void launch() throws Exception;

	public void init(Configuration configuration) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
		this.scanPackage(configuration.scanPakageName);
	}

	public void start(Configuration configuration){
		try {
			this.init(configuration);
			this.launch();
			this.successCallBack();
		}catch (Exception e){
			this.errorCallBack(e);
		}
	}

	public void successCallBack(){
		System.out.println("successCallBack...");
	}

	public void errorCallBack(Throwable e){
		System.out.println("errorCallBack..." + e);
	}

	public void scanPackage(String packageName) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {

		if(packageName != null && !"".equals(packageName.trim())){
			PackageScanner packageScanner = new PackageScanner(packageName);
			List<String> classNameList = packageScanner.scan();
			BusinessScanner businessScanner = new BusinessScanner();
			businessScanner.scannerMoController(classNameList);
			businessScanner.scannerMsgListener(classNameList);
		}
	}


}
