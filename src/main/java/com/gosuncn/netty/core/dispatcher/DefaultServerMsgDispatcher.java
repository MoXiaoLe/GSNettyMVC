package com.gosuncn.netty.core.dispatcher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

import com.gosuncn.netty.common.util.INetUtils;
import com.gosuncn.netty.common.util.JsonUtils.Node;
import com.gosuncn.netty.common.util.LoggerUtils;
import com.gosuncn.netty.core.common.InvokerHolder;
import com.gosuncn.netty.core.common.IocContainer;
import com.gosuncn.netty.core.model.DefaultDTO;
import com.gosuncn.netty.core.model.DefaultHeader;
import com.gosuncn.netty.core.model.DefaultRequestHeader;
import com.gosuncn.netty.core.model.GoContext;
import com.gosuncn.netty.core.model.GoRequest;
import com.gosuncn.netty.core.model.GoResponse;
import com.gosuncn.netty.core.model.GoSession;
import com.gosuncn.netty.core.model.MsgTypeEnum;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月28日
 * @description 消息分发器
 */
public class DefaultServerMsgDispatcher {
	
	private static DefaultServerMsgDispatcher dispatcher;
	
	private DefaultServerMsgDispatcher(){
		
	}
	
	public static DefaultServerMsgDispatcher newInstance(){
		
		if(dispatcher == null){
			synchronized (DefaultServerMsgDispatcher.class) {
				if(dispatcher == null){
					dispatcher = new DefaultServerMsgDispatcher();
				}
			}
		}
		
		return dispatcher;
	}
	
	public void dispathcher(GoRequest request,GoResponse response){
		
		DefaultRequestHeader header = (DefaultRequestHeader) request.getHeader();
		String url = header.getUrl().trim();
		int port = request.getGoContext().getPort();
		url = url.replaceFirst(INetUtils.REGEX_IP + ":" + port,"").replaceAll("/+", "/");
		InvokerHolder invoker = IocContainer.getInvokerHolder(url);
		if(invoker == null){
			LoggerUtils.warn("找不到对应的执行方法,响应状态码：1");
			DefaultHeader responseHeader = DefaultHeader.responseHeaderBuilder()
					.status((byte)1).build();
			DefaultDTO dto = DefaultDTO.buidler()
					.msgType(MsgTypeEnum.RESPONSE.getValue())
					.header(responseHeader).build();
			response.getChannel().writeAndFlush(dto);
		}else{
			try {
				Method method = invoker.getMethod();
				Class<?>[] paramsClazzs = method.getParameterTypes();
				Object[] params = new Object[paramsClazzs.length];
				for(int i=0;i<paramsClazzs.length;i++){
					params[i] =  buildByClazz(paramsClazzs[i],request,response);
				}
				Object retResult = method.invoke(invoker.getObj(),params);
				LoggerUtils.info("返回对象-{}",retResult);
				if(retResult != null){
					LoggerUtils.info("返回对象类型-{}",retResult.getClass().getName());
					
					DefaultHeader responseHeader = DefaultHeader
							.responseHeaderBuilder().build();
					DefaultDTO dto = DefaultDTO.buidler()
							.msgType(MsgTypeEnum.RESPONSE.getValue())
							.header(responseHeader).body(retResult).build();
					response.getChannel().writeAndFlush(dto);
				}
			} catch (Exception e) {
				LoggerUtils.warn("执行出错，响应状态码：2-{}",e.getMessage(),e);
				DefaultHeader responseHeader = DefaultHeader.responseHeaderBuilder()
						.status((byte)2).build();
				DefaultDTO dto = DefaultDTO.buidler()
						.msgType(MsgTypeEnum.RESPONSE.getValue())
						.header(responseHeader).build();
				response.getChannel().writeAndFlush(dto);
			}
		}
	}
	
	private Object buildByClazz(Class<?> clazz,GoRequest request,GoResponse response){
		
		if(clazz == GoRequest.class){
			return request;
		}else if(clazz == GoResponse.class){
			return response;
		}else if(clazz == GoContext.class){
			return IocContainer.getGoContext();
		}else if(clazz == GoSession.class){
			return request.getSession();
		}else{
			try {
				LoggerUtils.info("创建-{}",clazz.getName());
				Object obj = clazz.newInstance();
				Field[] fields = clazz.getDeclaredFields();
				for(Field field : fields){
					fillValue4Field(field,obj,request.getParamsNode());
				}
				return obj;
			} catch (Exception e) {
				LoggerUtils.warn("创建参数异常-{}",e.getMessage(),e);
			} 		
			return null;
		}
	}
	
	/**
	 * TODO 暂时未支持List、Map等集合数组  
	 * 暂时未支持List、Map等集合数组 
	 * 暂时未支持List、Map等集合数组 
	 * @param field
	 * @param obj
	 * @param request
	 * @throws Exception
	 */
	private boolean fillValue4Field(Field field,Object obj,Node node) throws Exception{
		
		field.setAccessible(true);
		Class<?> clazz = field.getType();
		Object value = null;
		Object temp = null;
		String key = field.getName();
		//LoggerUtils.info("填充-{}",key);
		if(key.equals(node.getKey())){
			//LoggerUtils.info("找到目标key-{}",key);
			//LoggerUtils.info("寻找目标clazz-{}",clazz.getName());
			temp = node.getValue();
			//LoggerUtils.info("解析出的clazz-{}",temp.getClass().getName());
			if(clazz == short.class || clazz == Short.class){
				if(temp instanceof Byte){
					value = ((Byte)temp).shortValue();
				}else if(temp instanceof Short){
					value = temp;
				}else if(temp instanceof Integer){
					value = ((Integer)temp).shortValue();
				}else if(temp instanceof Long){
					value = ((Long)temp).shortValue();
				}else if(temp instanceof BigDecimal){
					((BigDecimal)temp).shortValue();
				}
			}else if(clazz == int.class || clazz == Integer.class){
				if(temp instanceof Byte){
					value = ((Byte)temp).intValue();
				}else if(temp instanceof Short){
					value = ((Short)temp).intValue();
				}else if(temp instanceof Integer){
					value = temp;
				}else if(temp instanceof Long){
					value = ((Long)temp).intValue();
				}else if(temp instanceof BigDecimal){
					((BigDecimal)temp).intValue();
				}
			}else if(clazz == long.class || clazz == Long.class){
				if(temp instanceof Byte){
					value = ((Byte)temp).longValue();
				}else if(temp instanceof Short){
					value = ((Short)temp).longValue();
				}else if(temp instanceof Integer){
					value = ((Integer)temp).longValue();
				}else if(temp instanceof Long){
					value = temp;
				}else if(temp instanceof BigDecimal){
					value = ((BigDecimal)temp).longValue();
				}
			}else if(clazz == float.class || clazz == Float.class){
				if(temp instanceof Byte){
					value = ((Byte)temp).floatValue();
				}else if(temp instanceof Short){
					value = ((Short)temp).floatValue();
				}else if(temp instanceof Integer){
					value = ((Integer)temp).floatValue();
				}else if(temp instanceof Long){
					value = ((Long)temp).floatValue();
				}else if(temp instanceof Float){
					value = temp;
				}else if(temp instanceof BigDecimal){
					value = ((BigDecimal)temp).floatValue();
				}
			}else if(clazz == double.class || clazz == Double.class){
				if(temp instanceof Byte){
					value = ((Byte)temp).doubleValue();
				}else if(temp instanceof Short){
					value = ((Short)temp).doubleValue();
				}else if(temp instanceof Integer){
					value = ((Integer)temp).doubleValue();
				}else if(temp instanceof Long){
					value = ((Long)temp).doubleValue();
				}else if(temp instanceof Float){
					value = ((Float)temp).doubleValue();
				}else if(temp instanceof BigDecimal){
					value = ((BigDecimal)temp).doubleValue();
				}
			}else if(clazz == boolean.class || clazz == Boolean.class){
				if(temp instanceof Boolean){
					value = temp;
				}else{
					value = false;
				}
			}else if(clazz == byte.class || clazz == Byte.class){
				if(temp instanceof Byte){
					value = temp;
				}else if(temp instanceof Short){
					value = ((Short)temp).byteValue();
				}else if(temp instanceof Integer){
					value = ((Integer)temp).byteValue();
				}else if(temp instanceof Long){
					value = ((Long)temp).byteValue();
				}else if(temp instanceof BigDecimal){
					((BigDecimal)temp).byteValue();
				}
			}else if(clazz == String.class){
				if(temp instanceof String){
					value = temp;
				}else{
					value = "";
				}
			}else{
				// 数组或者对象
				value = clazz.newInstance();
				Field[] fields = value.getClass().getDeclaredFields();
				for(Field sonField : fields){
					List<Node> nodeList = node.getNodeList();
					if( nodeList != null){
						for(Node item : node.getNodeList()){
							if(fillValue4Field(sonField, value, item)){
								break;
							}
						}
					}
				}
			}
			field.set(obj, value);
			return true;
		}else{
			List<Node> nodeList = node.getNodeList();
			if( nodeList != null){
				for(Node item : node.getNodeList()){
					if(fillValue4Field(field, obj, item)){
						break;
					}
				}
			}
			return false; 
		}
	}
	

}
