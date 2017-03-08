package com.ch.study.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenhao on 2017/2/25.
 */
public class AIOServer {
    static List<AsynchronousSocketChannel> channelList = new ArrayList<>();
    public void startListener() throws IOException {
        ExecutorService exec = Executors.newCachedThreadPool();
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(exec,20);
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open(group)
                .bind(new InetSocketAddress("127.0.0.1",NServer.port));
        serverSocketChannel.accept(null,new AcceptHandler(serverSocketChannel));
    }
    class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel,Object>{
        private AsynchronousServerSocketChannel serverSocketChannel;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        public AcceptHandler(AsynchronousServerSocketChannel serverSocketChannel){
            this.serverSocketChannel = serverSocketChannel;
        }
        @Override
        public void completed(final AsynchronousSocketChannel socketChannel, Object attachment) {
            channelList.add(socketChannel);
            //为下一次准备
            serverSocketChannel.accept(null,this);
            socketChannel.read(byteBuffer, null, new CompletionHandler<Integer, Object>() {
                @Override
                public void completed(Integer result, Object attachment) {
                    byteBuffer.flip();
                    String content = StandardCharsets.UTF_8.decode(byteBuffer).toString();
                    System.out.println("服务器读取到信息:"+content);
                    //写到所有客户端
                    for(AsynchronousSocketChannel asc : channelList){
                        //                            asc.write(StandardCharsets.UTF_8.encode(content)).get();
                        asc.write(StandardCharsets.UTF_8.encode(content), null, new CompletionHandler<Integer, Object>() {
                            @Override
                            public void completed(Integer result, Object attachment) {

                            }
                            @Override
                            public void failed(Throwable exc, Object attachment) {

                            }
                        });
                    }
                    byteBuffer.clear();
                    socketChannel.read(byteBuffer,null,this);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    System.out.println("读取数据失败");
                    channelList.remove(socketChannel);
                }
            });
        }

        @Override
        public void failed(Throwable exc, Object attachment) {
            System.out.println("连接失败");
        }
    }

    public static void main(String[] args) throws IOException {
        new AIOServer().startListener();
        while(true){
            Thread.yield();
        }
    }
}
