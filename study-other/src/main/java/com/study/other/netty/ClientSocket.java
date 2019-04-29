package com.study.other.netty;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author cj-ch
 * @date 2019/4/29 上午9:54
 */
public class ClientSocket {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8010);
        System.out.println(socket.isConnected());
        try(
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), 1024);
                ){
            bufferedWriter.write("registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.add我门");
        }
        socket.close();

    }
}
