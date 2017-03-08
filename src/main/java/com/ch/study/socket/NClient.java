package com.ch.study.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenhao on 2017/2/25.
 */
public class NClient {
    private Selector selector;
    private SocketChannel socketChannel;

    public void init() throws IOException {
        selector = Selector.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",NServer.port);
        socketChannel = SocketChannel.open(inetSocketAddress);
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        //TODO
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ClientThread());
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            socketChannel.write(StandardCharsets.UTF_8.encode(scanner.nextLine()));
        }
    }
    private class ClientThread implements Runnable{

        @Override
        public void run() {
            try {
                while(selector.select()>0){
                    for (SelectionKey sk: selector.selectedKeys()) {
                        selector.selectedKeys().remove(sk);
                        if(sk.isReadable()){
                            SocketChannel sc = (SocketChannel) sk.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            String content="";
                            while(sc.read(byteBuffer)>0){
                                byteBuffer.flip();
                                content+=StandardCharsets.UTF_8.decode(byteBuffer);
                            }
                            System.out.println("聊天信息："+content);
                            sk.interestOps(SelectionKey.OP_READ);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NClient().init();
    }
}
