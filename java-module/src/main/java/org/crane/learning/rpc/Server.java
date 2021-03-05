package org.crane.learning.rpc;

/**
 * description: 服务接口
 * author: zhang
 * Date: 2021/3/6 12:01 上午
 */
public interface Server {
    void start();
    void stop();
    void register(Class<?> service,Class<?> serviceImpl);
}
