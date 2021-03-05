package org.crane.learning.rpc.module.impl;

import org.crane.learning.rpc.module.Student;

/**
 * description:实现测试类
 * author: zhang
 * Date: 2021/3/6 12:35 上午
 */
public class StudentImpl implements Student {
    @Override
    public int ageAdd(int age) {
        return (age+1);
    }
}
