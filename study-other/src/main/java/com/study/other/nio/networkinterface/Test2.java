package com.study.other.nio.networkinterface;

import org.apache.commons.lang3.ArrayUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Test2 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("localhost:" + localHost.getHostAddress());
        System.out.println("getCanonicalHostName:" + localHost.getCanonicalHostName());
        System.out.println("getHostName:" + localHost.getHostName());
        InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
        System.out.println(loopbackAddress);

        // 解析域名为Ip地址
        InetAddress[] allByName = InetAddress.getAllByName("www.baidu.com");
        if(ArrayUtils.isNotEmpty(allByName)){
            for (InetAddress inetAddress : allByName) {
                System.out.println("\tgetCanonicalHostName:" + inetAddress.getCanonicalHostName());
                System.out.println("\tgetHostName:" + inetAddress.getHostName());
                System.out.println("\tgetHostAddress:" + inetAddress.getHostAddress());
                System.out.println("\t+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        }
        System.out.println(InetAddress.getByName("localhost"));
        System.out.println(InetAddress.getByName("127.0.0.1"));
        System.out.println(Arrays.toString(InetAddress.getByName("www.baidu.com").getAddress()));
    }
}
