package org.crane.learning.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description: 服务中心
 * author: zhang
 * Date: 2021/3/5 11:53 下午
 */
public class ServerCenter implements Server {
    //map:提供服务端的所有可供client访问的interface 都register到map中

    private static Map<String, Class<?>> serviceRegister = new HashMap<>();
    private int port;
    //给定一个固定连接池,这里默认获取所有本机线程,每条线程可以处理一个client请求
    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static boolean isRunning = false;

    public ServerCenter(int port) {
        this.port = port;
    }

    /**
     * running server
     */
    @Override
    public void start() {
        ServerSocket server = null;

        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRunning = true;

        while (true) {
            //client每次请求一个连接,策服务端从连接池中获取一个线程对象去处理
            Socket socket = null;
            System.out.println("start server....");
            try {
                socket = server.accept();//等待client connection
            } catch (IOException e) {
                e.printStackTrace();
            }
            executor.execute(new ServiceTask(socket));
        }
    }

    @Override
    public void stop() {
        isRunning = false;
        executor.shutdown();
    }

    @Override
    public void register(Class<?> service, Class<?> serviceImpl) {
        serviceRegister.put(service.getName(), serviceImpl);
        System.out.println("注册了方法:" + service.getName());
    }

    private static class ServiceTask implements Runnable {
        private Socket socket;

        public ServiceTask(Socket socket) {
            this.socket = socket;
        }

        public ServiceTask() {
        }

        @Override
        public void run() { //线程执行的内容
            System.out.println("启动了一个新线程,当前线程为:" + Thread.currentThread().getName());
            ObjectOutputStream opt = null;
            ObjectInputStream input = null;

            try {
                //接收client的请求,处理该请求
                input = new ObjectInputStream(socket.getInputStream());
                //objInputStream对接收顺序有严格要求
                String serviceName = input.readUTF();
                String methodNmae = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] args = (Object[]) input.readObject();
                //找到当前的具体接口,如果没有注册,则注册一个
                Class<?> serviceClass = serviceRegister.get(serviceName);
                Method method = serviceClass.getMethod(methodNmae, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), args);
                opt=new ObjectOutputStream(socket.getOutputStream());
                opt.writeObject(result);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    if (opt != null) {
                        opt.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
