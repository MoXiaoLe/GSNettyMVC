package com.jiale.netty.core.scanner;

import com.jiale.netty.core.model.SystemConst;
import com.jiale.netty.core.util.ParseUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author mojiale@bluemoon.com.cn
 * @date 2019/10/9
 * @description 包扫描器
 */
public class PackageScanner {

    /**
     * 包名
     */
    private String packageName;
    /**
     * 包路径
     */
    private String packagePath;
    /**
     * 扫描注解类
     */
    private Class annotationClazz;
    /**
     * 类加载器
     */
    private ClassLoader classLoader;

    public PackageScanner(String packageName){
        this.packageName = packageName;
        this.packagePath = ParseUtils.packageNameToPath(packageName);
        this.classLoader = Thread.currentThread().getContextClassLoader();
    }

    public PackageScanner(String packageName,Class annotationClazz){
        this(packageName);
        this.annotationClazz = annotationClazz;
    }

    /**
     * 扫描packageName下的 className
     * @return
     */
    public List<String> scan() throws IOException {

        List<String> resultList = loadClassResources();
        if(this.annotationClazz != null){
            resultList = filterNotAnnotationClazz(resultList);
        }
        return resultList;
    }

    /**
     * 过滤出没有使用 AnnotationClazz 注解的className
     * @param classNameList
     * @return
     */
    private List<String> filterNotAnnotationClazz(List<String> classNameList){

        List<String> tempList = new ArrayList<>();
        classNameList.forEach(className ->{
            Class clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Annotation annotation = clazz.getAnnotation(this.annotationClazz);
            if(annotation != null){
                tempList.add(className);
            }
        });
        return tempList;
    }

    /**
     * 获取packageName下的 className
     * @return
     */
    private List<String> loadClassResources() throws IOException {

        List<String> classNameList = new ArrayList<>();
        Enumeration<URL> urlEnumeration = classLoader.getResources(this.packagePath);
        while (urlEnumeration.hasMoreElements()){
            URL url = urlEnumeration.nextElement();
            String protocol = url.getProtocol();
            String path;
            switch (protocol){
                case SystemConst.JAR:
                    path = ParseUtils.parsePathFromJarURL(url.getPath());
                    classNameList.addAll(scanJar(path));
                    break;
                case SystemConst.FILE:
                    path = ParseUtils.parsePathFromFileUrl(url.getPath());
                    classNameList.addAll(scanFile(path,packageName));
                    break;
            }
        }
        return classNameList;
    }

    private List<String> scanFile(String path,String packageName){

        File file = new File(path);
        List<String> classNameList = new ArrayList<>();
        // 得到目录下所有文件(目录)
        File[] files = file.listFiles();
        if (null != files) {
            int length = files.length;
            for (int i = 0 ; i < length ; ++i) {
                File f = files[i];
                // 判断是否还是一个目录
                if (f.isDirectory()) {
                    // 递归遍历目录
                    List<String> list = scanFile(f.getAbsolutePath(), ParseUtils.concat(packageName, ".", f.getName()));
                    classNameList.addAll(list);
                } else if (f.getName().endsWith(SystemConst.CLASS)) {
                    // 如果是以.class结尾
                    String className = ParseUtils.trimSuffix(f.getName());
                    // 如果类名中有"$",内部类不计算在内
                    if (-1 != className.lastIndexOf("$")) {
                        continue;
                    }
                    String result = ParseUtils.concat(packageName, ".", className);
                    classNameList.add(result);
                }
            }
        }

        return classNameList;
    }

    private List<String> scanJar(String path) throws IOException {
        JarFile jar = new JarFile(path);
        List<String> classNameList = new ArrayList<>();
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            if((name.startsWith(this.packagePath)) && (name.endsWith(SystemConst.CLASS)) ) {
                name = ParseUtils.trimSuffix(name);
                name = ParseUtils.pathToPackageName(name);
                classNameList.add(name);
            }
        }
        return classNameList;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public Class getAnnotationClazz() {
        return annotationClazz;
    }

    public void setAnnotationClazz(Class annotationClazz) {
        this.annotationClazz = annotationClazz;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
