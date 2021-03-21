package org.crane.learning.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

/**
 * description:
 * author: zhang
 * Date: 2021/3/14 8:49 下午
 */
public class StudyDemo {
    @Test
    public void test01() {
        List<Integer> integers = Arrays.asList(90, 100, 102, 1999, 89, 0);
        integers.sort(Integer::compare);
    }

    @Data
    @AllArgsConstructor
    class UserInfo {
        private String name;
        private String age;
        private String address;
    }

    @Test
    public void test02() {
        UserInfo userInfo = new UserInfo("张三", "24", "广州");
        Supplier<String> supplier = userInfo::getName;
        System.out.println(supplier.get());
    }

    @Test
    public void test03() {
        //首先查看String的Equals方法
        //满足条件 第一个参数是实例方法的调用者,第二个参数是实例方法的参数
        BiPredicate<String,String> bp=(x,y)->x.equals(y);
        BiPredicate<String,String> bp1=String::equals;
        System.out.println(bp1.test("1","1"));
    }

    @Test
    public void test04(){
        Supplier<List<UserInfo>> supplier=()->new ArrayList<>();
        Supplier<List<UserInfo>> supplier1=ArrayList::new;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class UserALL{
        private UserInfo userInfo;
    }
    @Test
    public void test05(){
        Supplier<UserALL> supplier=()->new UserALL(new UserInfo("","",""));
        Supplier<UserALL> supplier1=UserALL::new;
    }
}
