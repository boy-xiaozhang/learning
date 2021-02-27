package org.crane.flink.app;


import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.crane.flink.util.FlinkContextUtil;


/**
 * AppName:FlinkDemo01
 * Author:XiaoZhang@
 * Date:2020/12/3
 * Version:v1.0
 * Description:Flink集成Hive做批流一体
 */
public class FlinkDemo01 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = FlinkContextUtil.getFlinkEnv(false, true);
        StreamExecutionEnvironment bsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(bsEnv, bsSettings);

        tableEnv.executeSql("CREATE TABLE userinfo ( " +
                " id STRING, " +
                " name STRING, " +
                " age STRING, " +
                " address STRING, " +
                " ts STRING " +
                ") WITH ( " +
                " 'connector' = 'kafka', " +  //定义连接器为KAFKA
                " 'topic' = 'flinktest', " + //定义Topic
                " 'properties.bootstrap.servers' = '172.16.92.187:9092', " +
                " 'properties.group.id' = 'test', " +
                " 'scan.startup.mode' = 'latest-offset', " +
                " 'format' = 'json', " +
                " 'json.fail-on-missing-field' = 'false', " +
                " 'json.ignore-parse-errors' = 'true'" +
                ")");

        tableEnv.executeSql("CREATE TABLE print_table ( " +
                " id STRING, " +
                " name STRING, " +
                " age STRING, " +
                " address STRING, " +
                " ts STRING " +
                ") WITH ( " +
                " 'connector' = 'print' " +
                ")");

        tableEnv.executeSql("CREATE TABLE print_table2 ( " +
                " nuncount BIGINT " +
                ") WITH ( " +
                " 'connector' = 'print' " +
                ")");

       tableEnv.executeSql("INSERT INTO  print_table SELECT id,name,age,address,ts FROM userinfo");

        tableEnv.executeSql("INSERT INTO print_table2 SELECT COUNT(1) FROM userinfo");
    }
}
