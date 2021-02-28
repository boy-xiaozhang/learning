package org.crane.learning.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * description: 学习ClassLoader
 * author: zhang
 * Date: 2021/2/27 11:25 下午
 */
public class ClassLoaderDemo {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1)
                            + ".class";
                    //获取字节码文件
                    InputStream iS = getClass().getResourceAsStream(fileName);
                    if (iS == null) {
                        return super.loadClass(name);
                    }
                    byte[] bytes = new byte[iS.available()];
                    iS.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };

        //通过用户自定义类加载器加载的类
        Object o = classLoader.loadClass("org.crane.learning.classloader.ClassLoaderDemo").newInstance();
        System.out.println(o.getClass());
        System.out.println(ClassLoaderDemo.class);
        System.out.println(o instanceof ClassLoaderDemo);
    }
}
