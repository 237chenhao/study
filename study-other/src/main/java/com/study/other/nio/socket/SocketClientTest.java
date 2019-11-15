package com.study.other.nio.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClientTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8088);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("111".getBytes());
        outputStream.flush();


        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while(-1 != (len = inputStream.read(bytes))){
            System.out.println("收到:" + new String(bytes, 0 , len));
        }

        System.out.println("关闭连接");
        inputStream.close();
        outputStream.close();
        socket.close();

    }
}
