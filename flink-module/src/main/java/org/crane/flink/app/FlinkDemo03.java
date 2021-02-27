package org.crane.flink.app;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.SqlDialect;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.catalog.hive.HiveCatalog;

/**
 * Author @ZhangBaiQiang
 * Date 2020/12/18 10:39
 * Description 批流一体测试
 * Version 1.0
 */
public class FlinkDemo03 {
    public static void main(String[] args) {
        //定义环境上下文
        EnvironmentSettings env = EnvironmentSettings.newInstance().useBlinkPlanner().build();
        TableEnvironment tableEnv = TableEnvironment.create(env);


        //定义参数
        String name = "my-hive";
        String defaultDatabase = "default";
        String hiveConfDir = "G:\\zhangbaiqiang\\java_growing_up\\flink_learn\\src\\main\\resources\\hive";

        //创建HiveCataLog
        HiveCatalog hiveCatalog = new HiveCatalog(name, defaultDatabase, hiveConfDir);
        //在Flink的全局CataLog接口注册
        tableEnv.registerCatalog("my-hive", hiveCatalog);
        //将hive设置为当前会话目录
        tableEnv.useCatalog("my-hive");


        //更换使用Hive Dialect
        tableEnv.getConfig().setSqlDialect(SqlDialect.HIVE);

        //插入
        tableEnv.executeSql("INSERT INTO test.employeeinfo(work_no, name, deptname, `position`) VALUES (001,'ZhangSan','大数据部门','大数据开发')");
        //插入
        tableEnv.executeSql("INSERT INTO test.employeeinfo(work_no, name, deptname, `position`) VALUES (002,'LiSi','大数据部门','大数据实施')");
        //查询
        tableEnv.executeSql("SELECT * FROM  test.employeeinfo").print();

    }
}
