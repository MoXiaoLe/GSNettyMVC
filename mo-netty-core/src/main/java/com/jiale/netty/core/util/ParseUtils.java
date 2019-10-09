package com.jiale.netty.core.util;

import com.jiale.netty.core.model.SystemConst;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * @author mojiale@bluemoon.com.cn
 * @date 2019/10/8
 * @description 参数解析工具类
 */
public class ParseUtils {


    /**
     * 解析 body 为 Map<String,String[]>
     * @param body
     * @return
     */
    public  static Map<String,String[]> parseFormMap(byte[] body){

        String data = new String(body, SystemConst.CHARSET);
        String[] keyValues = data.replaceAll("&+", "&").split("&");
        int size = (int) (keyValues.length/0.75 + 1);
        Map<String, List<String>> tempMap = new HashMap<>(size);
        for(String keyValue : keyValues){
            if(!keyValue.contains("=")){
                continue;
            }
            int index = keyValue.indexOf("=");
            String key = keyValue.substring(0,index);
            String value = keyValue.substring(index+1);
            List<String> valueList = tempMap.get(key);
            if(valueList == null){
                valueList = new ArrayList<>();
                valueList.add(value);
                tempMap.put(key, valueList);
            }else{
                valueList.add(value);
            }
        }
        Map<String, String[]> paramsMap = new HashMap<>(size);
        for(Map.Entry<String, List<String>> entry : tempMap.entrySet()){
            List<String> list = entry.getValue();
            String[] arr = new String[list.size()];
            arr = list.toArray(arr);
            paramsMap.put(entry.getKey(), arr);
        }

        return paramsMap;
    }

    /**
     * 解析 url 为 string
     * @return
     */
    public static String parseUrl(byte[] url){
        String data = new String(url, SystemConst.CHARSET);
        String urlStr = data.replaceAll("/+", "/");
        return urlStr;
    }


    /**
     * path 转 packageName
     * /com/hello/world -> com.hello.world
     * @param path
     * @return
     */
    public static String pathToPackageName(String path){

        path = path.replaceAll("/+","/");
        if(path.startsWith("/")){
            path = path.substring(1);
        }
        return path.replaceAll("/",".");
    }

    /**
     * packageName 转为 path
     * com.hello.world -> /com/hello/world
     * @param packageName
     * @return
     */
    public static String packageNameToPath(String packageName){

        return packageName.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
    }

    /**
     * 去掉文件后缀名
     * @param fileName
     * @return
     */
    public static String trimSuffix(String fileName){

        int dotIndex = fileName.indexOf('.');
        if (-1 == dotIndex) {
            return fileName;
        }
        return fileName.substring(0, dotIndex);
    }

    /**
     * 从 JarURL 出解析出 jar 所在 path
     * jar:http://www.example.com/ex.jar!/com/demo/Class.class
     * jar:http://www.example.com/ex.jar!/
     * -> http://www.example.com/ex.jar
     * @param url
     * @return
     */
    public static String parsePathFromJarURL(String url) {
        int startPos = url.indexOf(':');
        int endPos = url.lastIndexOf('!');
        return url.substring(startPos + 1, endPos);
    }

    /**
     * 从 fileURL 出解析出所在 path
     * file:/E:/codeRepository/personal/MoNetty/mo-netty-server/target/classes/com.hello.world
     * ->/E:/codeRepository/personal/MoNetty/mo-netty-server/target/classes/com/hello/world
     * @param url
     * @return
     */
    public static String parsePathFromFileUrl(String url){

        return url.replaceAll("\\.|%5c", Matcher.quoteReplacement(File.separator));

    }

    public static String concat(Object ... args){

        StringBuilder sb = new StringBuilder();
        for (Object arg : args){
            sb.append(arg.toString());
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(pathToPackageName("//com/hello/world/"));
    }








}
