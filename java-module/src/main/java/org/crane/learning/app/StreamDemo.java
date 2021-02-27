package org.crane.learning.app;

import org.crane.learning.bean.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Module:StreamFeaturesDemo
 * CLassName: StreamDemo
 * Description:
 * Author: Crane
 * date: 2020/12/19 下午8:36
 */
public class StreamDemo {
    //创建一个Stream
    @Test
    public void test01() {
        //1 ,直接通过stream.of创建
        Stream<String> stream = Stream.of("123", "234", "456", "678");
        //2，将数组,集合转为Stream
        Stream<String> stream1 = Arrays.asList("123", "345", "456", "678").stream();//获取一个顺序流
        ArrayList<String> strings = new ArrayList<>();
        strings.add("123");
        Stream<String> stream2 = strings.parallelStream(); //获取一个并行流
        //3,使用函数Stream.iterate或者stream.generate创建
        //Stream.iterate需要两个参数，一个是初始化值，一个Function基于上一个元素的操作
        Stream<Integer> limit = Stream.iterate(2, (s) -> s * 2).limit(3);
        limit.forEach(System.out::println);
        //stream.generate是生成一个无限流，由参数Supplier 提供生成的元素，一直不停的执行当前的方法
        Stream.generate(() -> (56489 * Math.random())).limit(2).forEach(System.out::println);
    }


    @Test
    public void test02() {
        //对于玩过Spark，Flink的来说，理解Stream特性其实特别简单举例一下常用算子
        //map  filter distinct limit skip
        //创建一个Stream
        Stream<Integer> stream = Stream.of(123, 234, 456, 789);
        // stream.filter(a -> a > 200).skip(1).limit(1).forEach(System.out::println);
        Stream<Integer> stream1 = stream.map(t -> {
            return t += 200;
        }).filter(t -> t > 200);
    }

    //定义数据
    protected List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 38, 5555.55),
            new Employee("王五", 60, 6666.66),
            new Employee("赵六", 16, 7777.77),
            new Employee("田七", 18, 3333.33)
    );

    @Test
    public void test4() {
        //group by  min by max by filter map 这不和flink spark很像
        //切换并行流和顺序流parallel() 与sequential()
        String collect = employees.stream().parallel().map(Employee::getName).collect(Collectors.joining());
        System.out.println(collect);
    }

}