package org.crane.learning.rpc.test;

import org.crane.learning.rpc.RPCClient;
import org.crane.learning.rpc.module.Student;

import java.net.InetSocketAddress;

/**
 * @description:
 * @author: zhang
 * @Date: 2021/3/6 12:38 上午
 */
public class RPCClientTest {
    public static void main(String[] args) throws Exception {
        Student remoteProxyObj = RPCClient.getRemoteProxyObj(Class.forName("org.crane.learning.rpc.module.Student"),
                new InetSocketAddress("localhost", 9999));
        System.out.println(remoteProxyObj.ageAdd(10));
    }
}
