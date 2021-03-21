package org.crane.learning.app;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.io.Serializable;
import java.util.Optional;

/**
 * description: 学习Option类使用
 * author: zhang
 * Date: 2021/3/14 4:54 下午
 */
public class OptionDemo {
    @Getter
    @Setter
    class User01 implements Serializable {
        private String name;
        private String address;
    }

    @Test
    public void test01() {
        User01 user01 = new User01();
        //旧写法
        if (user01 != null) {
            if (user01.getAddress() != null) {
                new Throwable("address is null");
            }
        }else {
            user01.setAddress("广州");
            System.out.println(user01.getName());
        }
    }

    @Test
    public void test02(){
        User01 user01 = new User01();
        // method:of   调用构造方法
        // 根据源码我们可以看出,of依然使用的是构造函数,所以依然还是会exception
        Optional<User01> user = Optional.of(user01);
        // method:ofNullable 三目表达式
        // 根据其内容我们可以看出,不会抛出null,而是会返回一个具有空对象的optional
        Optional<User01> user011 = Optional.ofNullable(user01);
        // 究其结论,如果我们在写大项目是,optional是一个不错的选择,同时也要考虑万一真有空指针咋办...
    }

    @Test
    public void test03(){
        User01 user01 = new User01();
        User01 user011 = Optional.ofNullable(user01).orElse(createUser());
    }

    private User01 createUser(){
        User01 user01 = new User01();
        user01.setName("crane");
        return  user01;
    }

}
