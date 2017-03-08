package com.ch.study.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by chenhao on 2017/2/25.
 */
public class SimpleAIOServer {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        try(
                AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open()
                ){
            server.bind(new InetSocketAddress("127.0.0.1",NServer.port));
            while(true){
                Future<AsynchronousSocketChannel> socketChannelFuture = server.accept();
                AsynchronousSocketChannel channel = socketChannelFuture.get();
                channel.write(StandardCharsets.UTF_8.encode("欢迎来到AIO的世界"));
            }
        }
    }
}
