package org.crane.flink.util;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.IOException;

/**
 * Module:Flink Context Util
 * CLassName: FlinkContextUtil
 * Description: 封装Flink StreamingApi And Table 运行环境上下文
 * Author: Crane
 * date: 2020/12/3 下午11:04
 */
public class FlinkContextUtil {

    public static StreamExecutionEnvironment getFlinkEnv(boolean isEventTime, boolean islocal) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //set buffer time and size
        env.setBufferTimeout(100L);
        env.generateSequence(1, 10);
        //CheckPoint once a minute
        env.enableCheckpointing(60000);
        //GET checkPoint config
        CheckpointConfig checkpointConfig = env.getCheckpointConfig();
        //Set checkPoint exactly_once
        checkpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        //set the checkpoint interval to 30 seconds
        checkpointConfig.setMinPauseBetweenCheckpoints(30000);
        //set checkpoint timeout
        checkpointConfig.setCheckpointTimeout(3600000);
        //cancel save checkpoint
        checkpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        ExecutionConfig envConfig = env.getConfig();
        //重用对象
        boolean objectReuseEnabled = envConfig.isObjectReuseEnabled();
        //Whether to use event time
        if (isEventTime) {
            env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        }
        //if is not local start flink checkpoint
        if (!islocal) {
            try {
                env.setStateBackend(new RocksDBStateBackend("/home/zhang/flink/checkpoint/", true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return env;
    }


}
