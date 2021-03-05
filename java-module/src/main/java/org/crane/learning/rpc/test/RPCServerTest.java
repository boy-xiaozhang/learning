package org.crane.learning.rpc.test;

import org.crane.learning.rpc.Server;
import org.crane.learning.rpc.ServerCenter;
import org.crane.learning.rpc.module.Student;
import org.crane.learning.rpc.module.impl.StudentImpl;

/**
 * description:启动一个server类
 * author: zhang
 * Date: 2021/3/6 12:33 上午
 */
public class RPCServerTest {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerCenter serverCenter = new ServerCenter(9999);
                serverCenter.register(Student.class, StudentImpl.class);
                serverCenter.start();
            }
        }).start();
    }
}
