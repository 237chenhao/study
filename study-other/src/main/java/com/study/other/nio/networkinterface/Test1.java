package com.study.other.nio.networkinterface;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.util.CollectionUtils;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class Test1 {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while(networkInterfaces.hasMoreElements()){
            NetworkInterface n = networkInterfaces.nextElement();
            System.out.println("名称:" + n.getName());
            System.out.println("显示名称:" + n.getDisplayName());
            System.out.println("索引:" + n.getIndex());
            System.out.println("是否工作:" + n.isUp());
            System.out.println("是否回环:" + n.isLoopback());
            System.out.println("是否虚拟:" + n.isVirtual());
            System.out.println("是否点对点:" + n.isPointToPoint());
            System.out.println("MTU:" + n.getMTU());
            byte[] h = n.getHardwareAddress();
            System.out.print("MAC地址:");
            if(ArrayUtils.isNotEmpty(h)){
                for (byte b : h) {
                    System.out.print(Integer.toHexString(Byte.toUnsignedInt(b)) + " ");
                }
            }
            System.out.println();
            Enumeration<InetAddress> ip = n.getInetAddresses();
            while(ip.hasMoreElements()){
                InetAddress i = ip.nextElement();
                System.out.println("\tIP地址完全限定名:" + i.getCanonicalHostName());
                System.out.println("\tIP地址主机名:" + i.getHostName());
                System.out.println("\tIP地址地址字符串:" + i.getHostAddress());
                System.out.println("\tIP地址原始地址:" + Arrays.toString(i.getAddress()));
                System.out.println("\t+++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
            List<InterfaceAddress> ids = n.getInterfaceAddresses();
            System.out.println("\t网络接口信息");
            if(!CollectionUtils.isEmpty(ids)){
                for (InterfaceAddress i : ids) {
                    InetAddress address = i.getAddress();
                    if(address != null){
                        System.out.println("\tgetHostAddress:" + address.getHostAddress());
                    }
                    InetAddress broadcast = i.getBroadcast();
                    if(broadcast != null){
                        System.out.println("\t广播地址getHostAddress:" + broadcast.getHostAddress());
                    }
                    System.out.println("\t网络前缀长度:" + i.getNetworkPrefixLength());
                }
                System.out.println("\t==================================================");
            }
            System.out.println("--------------------------------------------------");
        }
    }
}
