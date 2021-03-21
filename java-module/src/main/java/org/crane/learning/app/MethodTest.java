package org.crane.learning.app;

/**
 * description:
 * author: zhang
 * Date: 2021/3/21 8:27 下午
 */
public interface MethodTest {
    //默认方法
    default void testDefaultMethod() {
        System.out.println("this is defined default method");
        System.out.println("hello word");
    }
    //静态方法
    static void testStaticMethod() {
        System.out.println("this is defined static method");
        System.out.println("hello word");
    }
    //抽象方法
    void testAbstaretMethod();
}
