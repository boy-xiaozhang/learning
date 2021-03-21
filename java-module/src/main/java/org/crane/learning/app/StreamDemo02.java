package org.crane.learning.app;

import org.crane.learning.bean.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * description:
 * author: zhang
 * Date: 2021/3/14 9:31 下午
 */
public class StreamDemo02 {
    protected List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 38, 5555.55),
            new Employee("王五", 60, 6666.66),
            new Employee("赵六", 16, 7777.77),
            new Employee("田七", 18, 3333.33)
    );

    @Test
    public void test01(){
        //查找员工年龄大于等于三十的
        employees.stream().map(t -> t.getAge() >= 30).forEach(System.out::println);
    }



    @Test
    public void test02(){
        //过滤出工资超过6000+的员工数
        long count = employees.stream().map(Employee::getSalary).filter(salary -> salary > 6000).count();
        System.out.println(count);
    }
}
