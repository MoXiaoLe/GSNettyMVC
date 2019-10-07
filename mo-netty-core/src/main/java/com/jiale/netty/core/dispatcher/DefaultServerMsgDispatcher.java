package com.jiale.netty.core.dispatcher;

import com.jiale.netty.core.util.INetUtils;
import com.jiale.netty.core.util.JsonUtils.Node;
import com.jiale.netty.core.util.LoggerUtils;
import com.jiale.netty.core.common.InvokerHolder;
import com.jiale.netty.core.common.IocContainer;
import com.jiale.netty.core.model.*;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
	
	public void dispathcher(MoRequest request, MoResponse response){
		
		DefaultRequestHeader header = (DefaultRequestHeader) request.getHeader();
		String url = header.getUrl().trim();
		int port = request.getGoContext().getPort();
		url = url.replaceFirst(INetUtils.REGEX_IP + ":" + port,"").replaceAll("/+", "/");
		InvokerHolder invoker = IocContainer.getInvokerHolder(url);
		if(invoker == null){
			DefaultHeader responseHeader = DefaultHeader.responseHeaderBuilder()
					.status(ResponseStatusCodeEnum.NOT_FOND.getCode()).build();
			DefaultDTO dto = DefaultDTO.buidler()
					.msgType(MsgTypeInface.RESPONSE)
					.header(responseHeader).build();
			response.getChannel().writeAndFlush(dto);
		}else{
			try {
				Method method = invoker.getMethod();
				LocalVariableTableParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
				String[] paramsNames = nameDiscoverer.getParameterNames(method);
				Class<?>[] paramsClazzs = method.getParameterTypes();
				Object[] params = new Object[paramsClazzs.length];
				for(int i=0;i<paramsClazzs.length;i++){
					params[i] =  buildByClazz(paramsNames[i],paramsClazzs[i],request,response);
				}
				Object retResult = method.invoke(invoker.getObj(),params);
				if(retResult != null){
					DefaultHeader responseHeader = DefaultHeader
							.responseHeaderBuilder().build();
					DefaultDTO dto = DefaultDTO.buidler()
							.msgType(MsgTypeInface.RESPONSE)
							.header(responseHeader).body(retResult).build();
					response.getChannel().writeAndFlush(dto);
				}
			} catch (Exception e) {
				LoggerUtils.info("服务端处理异常-{}", e);
				DefaultHeader responseHeader = DefaultHeader.responseHeaderBuilder()
						.status(ResponseStatusCodeEnum.ERROR.getCode()).build();
				DefaultDTO dto = DefaultDTO.buidler()
						.msgType(MsgTypeInface.RESPONSE)
						.header(responseHeader).build();
				response.getChannel().writeAndFlush(dto);
			}
		}
	}
	
	private Object buildByClazz(String fieldName, Class<?> clazz, MoRequest request, MoResponse response){
		
		if(clazz == MoRequest.class){
			return request;
		}else if(clazz == MoResponse.class){
			return response;
		}else if(clazz == MoContext.class){
			return IocContainer.getGoContext();
		}else if(clazz == MoSession.class){
			return request.getSession();
		}else{
			
			DefaultRequestHeader header = (DefaultRequestHeader)request.getHeader();
			try {

				if(header.getRequestType() == BodyTypeInface.JSON){
					if(clazz.isAssignableFrom(Collection.class)){
						// 单列集合
						throw new RuntimeException("不支持的类型-" + clazz.getName());
					}else if(clazz.isAssignableFrom(Map.class)){
						// 双列集合
						throw new RuntimeException("不支持的类型-" + clazz.getName());
					}else{
						// 普通对象
						Object obj = clazz.newInstance();
						Field[] fields = clazz.getDeclaredFields();
						for(Field field : fields){
							fillValue4Field(field,obj,request.getParamsNode());
						}
						return obj;
					}
				}else if(header.getRequestType() == BodyTypeInface.FORM){
					return request.getParameter(fieldName);
				}else if(header.getRequestType() == BodyTypeInface.SERIALIZER){
					Object obj = clazz.newInstance();
					if(obj instanceof Serializer){
						((Serializer)obj).readFromBytes(request.getParamsSerializerBytes());
					}
					return obj;		
				}
				
			} catch (Exception e) {
				LoggerUtils.warn("创建参数异常-{}",e.getMessage(),e);
			} 
			return null;
		}
	}
	
	/**
	 * TODO 暂时未支持List、Map等集合数组  
	 * 考虑获取List<T> 、Map<T> 的泛型T，然后使用json转换
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
		if(key.equals(node.getKey())){
			temp = node.getValue();
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
				
				if(clazz.isAssignableFrom(Collection.class)){
					throw new RuntimeException("不支持的类型-" + clazz.getName());
				}else if(clazz.isAssignableFrom(Map.class)){
					throw new RuntimeException("不支持的类型-" + clazz.getName());
				}else{
					// 普通对象
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
			}
			field.set(obj, value);
			return true;
		}else{
			List<Node> nodeList = node.getNodeList();
			if( nodeList != null){
				for(Node item : node.getNodeList()){
					if(fillValue4Field(field, obj, item)){
						return true; 
					}
				}
			}
			return false; 
		}
	}
	

}
