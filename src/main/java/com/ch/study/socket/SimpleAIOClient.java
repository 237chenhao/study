package com.ch.study.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

/**
 * Created by chenhao on 2017/2/25.
 */
public class SimpleAIOClient {
    public static void main(String[] args) {
        try(AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open()){
            socketChannel.connect(new InetSocketAddress("127.0.0.1",NServer.port)).get();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer).get();
            byteBuffer.flip();
            System.out.println("服务器信息："+ StandardCharsets.UTF_8.decode(byteBuffer));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
