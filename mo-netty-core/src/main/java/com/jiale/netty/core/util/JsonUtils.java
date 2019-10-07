package com.jiale.netty.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author mojiale66@163.com
 * @date 2018年9月19日
 * @description json 转化工具类
 */
public class JsonUtils {
	
	public static String toJsonString(Object obj){
		
		if(obj == null){
			return "";
		}
		
		return JSON.toJSONString(obj);
		
	}
	
	public static Object toJsonObject(String jsonString){
		
		if(StringUtils.isEmpty(jsonString)){
			return null;
		}
		
		return JSON.parse(jsonString);
		
	}
	
	/**
	 * 判断是否标准json字符串
	 * @param jsonStrs
	 * @return
	 */
	public static boolean isStandardJson(String jsonString){
		
		try{
			Object obj = toJsonObject(jsonString);
			if(obj != null){
				return true;
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}
	
	public static Node getNodeFromJsonString(String jsonString){
		
		Object obj = toJsonObject(jsonString);
		if(obj == null){
			return null;
		}
		
		Node node = new Node();
		node.setKey("GO_ROOT");
		List<Node> sonNodeList = null;
		if(obj instanceof JSONObject){
			sonNodeList = handleJsonObject((JSONObject)obj);
		}else if(obj instanceof JSONArray){
			sonNodeList = handleJsonArray((JSONArray)obj);
		}
		node.setNodeList(sonNodeList);
		return node;
	}
	
	private static List<Node> handleJsonObject(JSONObject obj){
		
		Set<Entry<String, Object>> entrySet = obj.entrySet();
		List<Node> nodeList = new ArrayList<>();
		if(entrySet != null){
			for(Entry<String, Object> entry : entrySet){
				Node node = new Node();
				String key = entry.getKey();
				Object value = entry.getValue();
				node.setKey(key);
				if(value instanceof JSONObject){
					node.setNodeList(handleJsonObject((JSONObject)value));
				}else if(value instanceof JSONArray){
					node.setNodeList(handleJsonArray((JSONArray)value));
				}else{
					node.setValue(value);
				}
				nodeList.add(node);
			}
		}
		
		return nodeList;
	}
	
	private static List<Node> handleJsonArray(JSONArray array){
		
		List<Node> nodeList = new ArrayList<>();
		for(int i=0;i<array.size();i++){
			Node node = new Node();
			node.setKey("ARRAY_KEY_" + (i + 1));
			Object obj = array.get(i);
			if(obj instanceof JSONObject){
				node.setNodeList(handleJsonObject((JSONObject)obj));
			}else if(obj instanceof JSONArray){
				node.setNodeList(handleJsonArray((JSONArray)obj));
			}
			nodeList.add(node);
		}
		return nodeList;
	}
	
	public static class Node{
		
		private String key;
		private Object value;
		private List<Node> nodeList;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		public List<Node> getNodeList() {
			return nodeList;
		}
		public void setNodeList(List<Node> nodeList) {
			this.nodeList = nodeList;
		}
	}
	
	
	
	
	
	
	public static void main(String[] args){
		
		Student student = new Student();
		student.setStudent(new Student());
		String str1 = toJsonString(student);
		Node node1 = getNodeFromJsonString(str1);
		System.out.println(JsonUtils.toJsonString(node1));
		
		List<Student> stuList = new ArrayList<Student>();
		stuList.add(student);
		stuList.add(student);
		String str2 = toJsonString(stuList);
		Node node2 = getNodeFromJsonString(str2);
		System.out.println(JsonUtils.toJsonString(node2));
		
	}
	
	
	static class Student{
		
		private String nameNiHAli = "xiaomo";
		private int age = 23;
		private String home = "zhaoqing";
		private Student student;
		public String getNameNiHAli() {
			return nameNiHAli;
		}
		public void setNameNiHAli(String nameNiHAli) {
			this.nameNiHAli = nameNiHAli;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getHome() {
			return home;
		}
		public void setHome(String home) {
			this.home = home;
		}
		public Student getStudent() {
			return student;
		}
		public void setStudent(Student student) {
			this.student = student;
		}
		
	}

}
