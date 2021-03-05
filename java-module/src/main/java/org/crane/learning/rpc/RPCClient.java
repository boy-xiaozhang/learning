package org.crane.learning.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * description:学习RPC
 * author: zhang
 * Date: 2021/3/5 11:39 下午
 */
public class RPCClient <T>{
    //serviceName 请求的接口名
    //addr 请求的ip:port
    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress addr){
        //1,将本地接口调用转换成JDK动态代理,在动态代理中实现接口的远程调用
        //a,类加载器 需要代理那个类,就需要将那个类的加载器传入第一个参数
        //b,需要代理的对象,具备哪些功能 --这里写接口
        // 为什么是数组:因为java中单继承,多实现,可能有多个接口
        // 类似字符串数据 String[] str=new String[]{'aa','bb'}
        //c,handlerIntecepter 拦截器
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class<?>[]{serviceInterface}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                Socket socket=null;
                ObjectOutputStream opt=null;
                ObjectInputStream input=null;
                try {
                    //2,Create socker client
                    socket=new Socket();
                    socket.connect(addr);
                    //3,将远程服务调用所需的接口类,方法名,参数列表等编码后发送给server提供者
                    opt=new ObjectOutputStream(socket.getOutputStream());
                    opt.writeUTF(serviceInterface.getName());
                    opt.writeUTF(method.getName());
                    opt.writeObject(method.getParameterTypes());
                    opt.writeObject(args);

                    //4.同步阻塞等待服务器返回答应,获取答应后返回
                    input=new ObjectInputStream(socket.getInputStream());

                    return input.readObject();
                } finally {
                    if (socket!=null){
                        socket.close();
                    }
                    if (opt != null) {
                        opt.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                }
            }
        });

    }
}
