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
        long x = System.currentTimeMillis();
        System.out.println(x);
        Socket socket = new Socket("47.97.161.238", 10911);
        System.out.println(System.currentTimeMillis() - x);
        System.out.println(socket.isConnected());
        try(
                InputStream inputStream = socket.getInputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), 1024);
                ){
            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            System.out.println(read);
            System.out.println(bytes);
            bufferedWriter.write("registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.addResourceHandler(registry.add我门");
        }



        socket.close();

    }
}
