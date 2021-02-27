package org.crane.flink.app;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.crane.common.GlobalPublicVariables;
import org.crane.flink.bean.OrderInformation;
import org.crane.flink.util.FlinkContextUtil;

import java.time.Duration;
import java.util.Properties;

/**
 *  Module:FlinkDemo02
 *  CLassName: FlinkDemo02
 *  Description: 所有数据都是为了复习伪造的，如果需要数据发送到邮箱  zhangbq3306@163.com
 *  Author: Crane
 *  date: 2020/12/12 下午4:59
 */
public class FlinkDemo02 {
    public static final String KAFKA_GROUP_ID = "test";
    public static final String KAFKA_TOPIC = "flink";

    public static void main(String[] args) {
        Properties pro = new Properties();
        pro.setProperty("bootstrap.servers", GlobalPublicVariables.KAFKA_SERVER);
        pro.setProperty("group.id", KAFKA_GROUP_ID);

        //复习一下Flink中的Window，Watermark
        final StreamExecutionEnvironment env = FlinkContextUtil.getFlinkEnv(true, true);

        DataStreamSource<String> input = env.addSource(new FlinkKafkaConsumer<>(KAFKA_TOPIC, new SimpleStringSchema(), pro).setStartFromLatest());
        //todo 未写完
        SingleOutputStreamOperator<OrderInformation> data = input.map((line) -> {
            return new ObjectMapper().readValue(line, OrderInformation.class);
        }).returns(TypeInformation.of(OrderInformation.class)).
                assignTimestampsAndWatermarks(WatermarkStrategy
                        .<OrderInformation>forBoundedOutOfOrderness(Duration.ofMillis(1000))
                        .withTimestampAssigner((orderInformation, timestamp) -> Long.parseLong(orderInformation.getOrderTime()))).startNewChain();

        //需求每五秒统计一次金额
        //统计要求，每隔五秒统计一下五秒内的，而不是每隔五秒统计一下滚动，而是滑动

        //补录一下备注，执行了一下源码：一个大坑就是窗口的开始时间，以前用窗口时我一直以为是第一条数据的时间，其实并不然
        SingleOutputStreamOperator<OrderInformation> sum = data.windowAll(TumblingEventTimeWindows.of(Time.milliseconds(5000)))
                .sum("amount").startNewChain();
        //滑动窗口  五秒滑动一次窗口，每次计算十秒的数据
//        SingleOutputStreamOperator<OrderInformation> sum = data.windowAll(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(5)))
//                .sum("amount");

        sum.print();


        try {
            //打印执行计划
            System.out.println(env.getExecutionPlan());
            env.execute("TestFLink");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
