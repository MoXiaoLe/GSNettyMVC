package com.jiale.netty.core.scanner;

import com.jiale.netty.core.annotation.MoController;
import com.jiale.netty.core.annotation.MoMsgListener;
import com.jiale.netty.core.annotation.MoRequestMapping;
import com.jiale.netty.core.common.InvokerHolder;
import com.jiale.netty.core.common.IocContainer;
import com.jiale.netty.core.listener.MsgListener;
import com.jiale.netty.core.util.LoggerUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author mojiale@bluemoon.com.cn
 * @date 2019/10/9
 * @description 业务扫描器
 */
public class BusinessScanner {


    public void scannerMsgListener(List<String> classNameList) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        if(classNameList == null || classNameList.isEmpty()){
            return;
        }
        for(String className : classNameList){
            Class<?> clazz = Class.forName(className);
            MoMsgListener msgListener = clazz.getAnnotation(MoMsgListener.class);
            if(msgListener != null){
                Object bean = clazz.newInstance();
                if(bean instanceof MsgListener){
                    IocContainer.putMsgListener("msgListener", (MsgListener) bean);
                }
            }
        }
    }

    public void scannerMoController(List<String> classNameList) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        if(classNameList == null || classNameList.isEmpty()){
            return;
        }
        for(String className : classNameList){
            Class<?> clazz = Class.forName(className);
            MoController controller = clazz.getAnnotation(MoController.class);
            MoRequestMapping requestMapping = clazz.getAnnotation(MoRequestMapping.class);
            String basePath = requestMapping != null ? requestMapping.path() : "";
            if(controller != null){
                Method[] methods = clazz.getMethods();
                if(methods != null){
                    for(Method method : methods){
                        MoRequestMapping reqMapping = method.getAnnotation(MoRequestMapping.class);
                        if(reqMapping == null){
                            continue;
                        }
                        String path = reqMapping.path();
                        InvokerHolder invokerHolder = InvokerHolder.valueOf(clazz.newInstance(), method);
                        String url = (basePath + "/" + path).replaceAll("/+", "/").trim();
                        IocContainer.putInvokerHolder(url, invokerHolder);
                    }
                }
            }
        }
    }

}
