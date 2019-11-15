package com.study.other.nio.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088, 1024, InetAddress.getByName("127.0.0.1"));

        System.out.println("开始启动socker服务端");
        Socket socket = serverSocket.accept();
        System.out.println("连接到一个客户端");
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        byte[] bytes = new byte[1024];
        int len = 0;
        len = inputStream.read(bytes);
        System.out.println("服务端接收到:" + new String(bytes, 0 , len));

        System.out.println("响应客户端请求");
        outputStream.write("abc".getBytes());
        outputStream.flush();

        System.out.println("退出服务");
        inputStream.close();
        outputStream.close();
        serverSocket.close();
    }
}
