package org.crane.learning.app;

/**
 * description:
 * author: zhang
 * Date: 2021/3/21 8:36 下午
 */
public class MethodTestImpl implements MethodTest{
    @Override
    public void testAbstaretMethod() {
        System.out.println("123");
    }

    public static void main(String[] args) {
        MethodTestImpl methodTest = new MethodTestImpl();
        methodTest.testAbstaretMethod();
        methodTest.testDefaultMethod();
        MethodTest.testStaticMethod();
    }
}
