package com.gosuncn.netty.core.model;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.gosuncn.netty.core.common.BufferFactory;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月18日
 * @description 序列化基类
 */
public abstract class Serializer {
	
	public static final Charset CHARSET = Charset.forName("UTF-8");
	
	/**反序列化缓冲区*/
	protected ByteBuf readBuf;
	
	/**序列化缓冲区*/
	protected ByteBuf writeBuf;
	
	/**反序列化*/
	protected abstract void read();
	
	/**序列化*/
	protected abstract void write();
	
	public short readShort(){
		return readBuf.readShort();
	}
	
	public void writeShort(Short value){
		writeBuf.writeShort(value == null ? 0 : value);
	}
	
	public int readInt(){
		return readBuf.readInt();
	}
	
	public void writeInt(Integer value){
		writeBuf.writeInt(value == null ? 0 : value);
	}
	
	public long readLong(){
		return readBuf.readLong();
	}
	
	public void writeLong(Long value){
		writeBuf.writeLong(value == null ? 0 : value);
	}
	
	public float readFloat(){
		return readBuf.readFloat();
	}
	
	public void writeFloat(Float value){
		writeBuf.writeFloat(value == null ? 0f: value);
	}
	
	public double readDouble(){
		return readBuf.readDouble();
	}
	
	public void writeDouble(Double value){
		writeBuf.writeDouble(value == null ? 0.0 : value);
	}
	
	public boolean readBoolean(){
		return readBuf.readBoolean();
	}
	
	public void writeBoolean(Boolean value){
		writeBuf.writeBoolean(value == null ? false : value);
	}
	
	public byte readByte(){
		return readBuf.readByte();
	}
	
	public void writeByte(Byte value){
		writeBuf.writeByte(value == null ? 0 : value);
	}
	
	public byte[] readBytes(byte[] data){
		readBuf.readBytes(data);
		return data;
	}
	
	public void writeBytes(byte[] data){
		writeBuf.writeBytes(data == null ? (new byte[0]): data);
	}
	
	public String readString(){
		
		short length = readShort();
		if(length <= 0){
			return "";
		}
		
		byte[] data = new byte[length];
		data = readBytes(data);
		return new String(data,CHARSET);
	}

	public void writeString(String value){
		if(StringUtils.isEmpty(value)){
			// 如果为空值写入0作分隔符
			writeShort((short)0);
			return;
		}
		
		byte[] data = value.getBytes(CHARSET);
		short length = (short) data.length;
		writeShort(length);
		writeBytes(data);
	}
	
	public <T>List<T> readList(Class<T> clazz){
		
		short size = readShort();
		List<T> list = new ArrayList<T>();
		for(int i=0;i<size;i++){
			list.add(readObject(clazz));
		}
		return list;
	}
	
	public <K,V> Map<K,V> readMap(Class<K> keyClazz,Class<V> valueClazz){
		
		short size = readShort();
		Map<K, V> map = new HashMap<K, V>();
		for(int i=0;i<size;i++){
			K key = readObject(keyClazz);
			V value = readObject(valueClazz);
			map.put(key, value);
		}
		return map;
	}
	
	public void writeList(List<?> list,Class<?> clazz){
		if(list == null || list.isEmpty()){
			writeShort((short)0);
			return;
		}
		
		writeShort((short)list.size());
		for(Object item : list){
			writeObject(item,clazz);
		}
	}
	
	public <K,V> void writeMap(Map<K,V> map,Class<?> keyClazz,Class<?> valueClazz){
		
		if(map == null || map.isEmpty()){
			writeShort((short)0);
			return;
		}
		
		writeShort((short)map.size());
		for(Entry<K, V> keyValue : map.entrySet()){
			writeObject(keyValue.getKey(),keyClazz);
			writeObject(keyValue.getValue(),valueClazz);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T readObject(Class<T> clazz){
		
		Object t = null;
		if(clazz == short.class || clazz == Short.class){
			t = readShort();
		}else if(clazz == int.class || clazz == Integer.class){
			t = readInt();
		}else if(clazz == long.class || clazz == Long.class){
			t = readLong();
		}else if(clazz == float.class || clazz == Float.class){
			t = readFloat();
		}else if(clazz == double.class || clazz == Double.class){
			t = readDouble();
		}else if(clazz == boolean.class || clazz == Boolean.class){
			t = readBoolean();
		}else if(clazz == byte.class || clazz == Byte.class){
			t = readByte();
		}else if(clazz == String.class){
			t = readString();
		}else if(Serializer.class.isAssignableFrom(clazz)){
			// clazz是Serializer的子类
			short flag = readShort();
			if(flag == 1){
				try {
					Serializer temp = (Serializer)clazz.newInstance();
					temp.readFromByteBuf(this.readBuf);
					t = temp;
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}else{
			throw new RuntimeException("不支持的反序列化类型-" + clazz.getName());
		}
		
		return (T)t; 
	}
	
	public <T> void writeObject(Object obj,Class<T> clazz){
		
		if(clazz == short.class || clazz == Short.class){
			writeShort((Short)obj);
		}else if(clazz == int.class || clazz == Integer.class){
			writeInt((Integer)obj);
		}else if(clazz == long.class || clazz == Long.class){
			writeLong((Long)obj);
		}else if(clazz == float.class || clazz == Float.class){
			writeFloat((Float)obj);
		}else if(clazz == double.class || clazz == Double.class){
			writeDouble((Double)obj);
		}else if(clazz == boolean.class || clazz == Boolean.class){
			writeBoolean((Boolean)obj);
		}else if(clazz == byte.class || clazz == Byte.class){
			writeByte((Byte)obj);
		}else if(clazz == String.class){
			writeString((String)obj);
		}else if(Serializer.class.isAssignableFrom(clazz)){
			// clazz是Serializer的子类
			if(obj == null){
				// null 对象写入0作为分隔符
				writeShort((short)0);
			}else{
				// 非null对象写入1作为分隔符
				writeShort((short)1);
				((Serializer)obj).writeToByteBuf(this.writeBuf);
			}
		}else{
			throw new RuntimeException("不支持的序列化类型-" + clazz.getName());
		}
		
	}
	
	/**
	 * 序列化到一个byteBuf中
	 * @param byteBuf
	 * @return ByteBuf
	 */
	public ByteBuf writeToByteBuf(ByteBuf byteBuf){
		this.writeBuf = byteBuf;
		write();
		return byteBuf;
	}
	
	/**
	 * 序列化到一个本地byteBuf
	 * @return ByteBuf
	 */
	public ByteBuf writeToLocalByteBuf(){
		this.writeBuf = BufferFactory.buildBuff();
		write();
		return writeBuf;
	}
	
	/**
	 * 从一个byteBuf中反序列化
	 * @param byteBuf
	 * @return Serializer 序列化对象
	 */
	public Serializer readFromByteBuf(ByteBuf byteBuf){
		this.readBuf = byteBuf;
		read();
		return this;
			
	}
	
	/**
	 * 从byte[]中反序列化
	 * @param data
	 * @return
	 */
	public Serializer readFromBytes(byte[] data){
		this.readBuf = BufferFactory.buildBuff(data);
		read();
		readBuf.clear();
		ReferenceCountUtil.release(readBuf);
		return this;
	}
	
	/**
	 * 获取序列化后的字节数组
	 * @return
	 */
	@JSONField(serialize = false)
	public byte[] getBytes(){
		
		this.writeToLocalByteBuf();
		int writeIndex = this.writeBuf.writerIndex();
		byte[] data = null;
		if(writeIndex == 0){
			data = new byte[0]; 
		}else{
			data = new byte[writeIndex];
			this.writeBuf.readBytes(data);
		}
		this.writeBuf.clear();
		ReferenceCountUtil.release(writeBuf);
		
		return data;
	}
	
	
}
