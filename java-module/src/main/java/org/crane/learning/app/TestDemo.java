package org.crane.learning.app;

/**
 * @description:
 * @author: zhang
 * @Date: 2021/2/24 10:23 下午
 */
public class TestDemo {
    public static void main(String[] args) {
        //bootClassPath ，就是 BootStrapClassLoader 加载的系统资源
        String property = System.getProperty("sun.boot.class.path");
        System.out.println("BootClassPath:"+property);
    }
}
