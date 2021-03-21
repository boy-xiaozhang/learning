package org.crane.learning.app;


import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.crane.learning.bean.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * description: Lambda Demos
 * author: zhang
 * Date: 2021/2/21 7:51 下午
 */
public class LambdaDemo {
    @Test
    public void test01() {
        //如下所示，当我们没有使用lambda表达式时，关键代码其实就是Integer.compare(o1, o2);
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };
        TreeSet<Integer> treeSet = new TreeSet<>(com);
    }

    @Test
    public void test02() {
        //我们使用lambda表示一下,如下所示灰常简单
        TreeSet<Integer> integers = new TreeSet<>(Integer::compare);
    }

    //接下来我们以小例子来测试Lambda  表达式,帮助尽快上手
    //定义数据
    protected List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 38, 5555.55),
            new Employee("王五", 60, 6666.66),
            new Employee("赵六", 16, 7777.77),
            new Employee("田七", 18, 3333.33)
    );

    //首先我们查找年龄大于等三十的员工信息并输出
    public List<Employee> filterEmployeeByAge(List<Employee> data) {
        ArrayList<Employee> list = new ArrayList<>();
        for (Employee datum : data) {
            if (datum.getAge() >= 30) {
                list.add(datum);
            }
        }
        return list;
    }

    @Test
    public void test03() {
        List<Employee> employees = filterEmployeeByAge(this.employees);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
        //总体来说,查找年龄大于或者等于30的员工信息,使用常规遍历集合的方式稍显复杂了。
        //例如,需求发生了变化:获取当前公司中员工工资大于或者等于5000的员工信息。
        //此时,我们不得不再次创建一个按照工资过滤的方法。
    }

    //下面我们来一首lambda表达式
    //配合StreamApi一起使用，我只想说秀哇
    @Test
    public void test04() {
        employees.stream().filter(e -> e.getAge() >= 30).limit(2).forEach(System.out::println);
    }

    //接下来开始学习，首先看一下匿名表达式是怎么成为lambda表达式的
    public static void main(String[] args) {
        Runnable ru = new Runnable() {

            @Override
            public void run() {
                System.out.println("我是子线程");
            }

        };
        new Thread(ru).start();

        //lambda表达式如下
        //匿名内部类作为参数传递到Lambda表达式作为参数传递
        Runnable r = () -> System.out.println("我是子线程");
    }

    /*  Lambda表达式的语法
    Lambda表达式在Java语言中引入了 “->” 操作符, “->” 操作符被称为Lambda表达式的操作符或者箭头
    操作符,它将Lambda表达式分为两部分:
    左侧部分指定了Lambda表达式需要的所有参数。
    Lambda表达式本质上是对接口的实现,Lambda表达式的参数列表本质上对应着接口中方法的参数列
    表。
    右侧部分指定了Lambda体,即Lambda表达式要执行的功能。
    Lambda体本质上就是接口方法具体实现的功能。*/


    @Test
    public void lambdaDemo01() {
        //无参数，无返回值
        Runnable r = () -> System.out.println("Hello Lambda");
        //需要一个参数,并且无返回值
        Consumer<String> func = System.out::println;
        //Lambda需要两个参数,并且有返回值
        BinaryOperator<Integer> bo = Integer::sum;
    }
    //接下来说说函数式接口
    //Lambda表达式需要函数式接口的支持,所以,我们有必要来说说什么是函数式接口。
    //只包含一个抽象方法的接口,称为函数式接口。
    //可以通过 Lambda 表达式来创建该接口的对象。(若 Lambda表达式抛出一个受检异常,那么该
    //异常需要在目标接口的抽象方法上进行声明)。
    //可以在任意函数式接口上使用 @FunctionalInterface 注解,这样做可以检查它是否是一个函数式
    //接口,同时 javadoc 也会包含一条声明,说明这个接口是一个函数式接口。

    @FunctionalInterface
    public interface Map<T> {
        T getValue(T t);
    }

    public <T> T handlerString(Map<T> fun, T str) {
        return fun.getValue(str);
    }

    //如下所述，上面代码块为我们定义的接口和代码
    //接口规范是返回的指需要和参数类型一致
    //我们定义了一个方法，来运行接口
    //第一步首先调用方法，传入接口代码，然后入参
    //这里我们就可以看出来  lambda
    @Test
    public void test1() {
        List<String> strings = this.handlerString((List<String> s) -> s.subList(0, 2), Arrays.asList("123", "123", "123"));
        System.out.println(strings);
    }

    @FunctionalInterface
    public interface Transform<R, T> {
        R getValue(T t);
    }

    public <R, T> R getEmployeeFilter(Transform<R, T> myFun, T str) {
        return myFun.getValue(str);
    }

    @Test
    public void test2() {
        Stream<Employee> test = this.getEmployeeFilter(data -> data.stream().filter(e -> e.getAge() >= 30).limit(1), employees);
        test.forEach(System.out::println);
    }


    @Test
    public void test111() {
        Optional<Integer> reduce = Stream.generate(() -> {
            Random random = new Random();
            return random.nextInt(1000);
        }).limit(10).reduce(Integer::sum);
        Integer integer = reduce.orElse(6666);
        System.out.println(integer);
    }

    @Test
    public void printHelloWorld() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("HelloWorld");
            }
        }).start();
    }

    @Test
    public void lambdaHelloWorld() {
        new Thread(() -> System.out.println("Hello World")).start();
    }

    @FunctionalInterface
    interface Calculator<T> {
        T operation(T t1, T t2);
    }

    private <T> T add(T t1, T t2,Calculator<T> calculator) {
        return calculator.operation(t1,t2);
    }

    @Test
    public void lambdaStudy(){
        Integer add = this.add(1, 2, (v1, v2) -> v1 + v2);
        System.out.println(add);
    }

    public void print(Consumer<String> msg){
        msg.accept("6666");
    }

    @Test
    public void test104(){
        print(System.out::println);
    }
}
