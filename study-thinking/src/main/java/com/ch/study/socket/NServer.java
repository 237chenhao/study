package com.ch.study.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by chenhao on 2017/2/25.
 */
public class NServer {
    private Selector selector;
    static final int port=30000;
    public void init() throws IOException {
        selector = Selector.open();
        ServerSocketChannel server = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",port);
        server.configureBlocking(false);
        server.bind(inetSocketAddress);
        server.register(selector, SelectionKey.OP_ACCEPT);
        while(selector.select()>0){
            for(SelectionKey sk : selector.selectedKeys()){
                selector.selectedKeys().remove(sk);
                if(sk.isAcceptable()){
                    SocketChannel sc = server.accept();
                    sc.configureBlocking(false);
                    sc.register(selector,SelectionKey.OP_READ);
                    //将Sk 对应的Channel设置成准备接收其他的请求
                    sk.interestOps(SelectionKey.OP_ACCEPT);
                }
                if(sk.isReadable()){
                    SocketChannel sc = (SocketChannel) sk.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    String conent = "";
                    try {
                        while(sc.read(byteBuffer)>0){
                            byteBuffer.flip();
                            conent+= StandardCharsets.UTF_8.decode(byteBuffer);
                        }
                        System.out.println("读取的数据："+conent);
                        sk.interestOps(SelectionKey.OP_READ);
                    } catch (IOException e) {
                        e.printStackTrace();
                        sk.cancel();
                        if(sk.channel()!=null){
                            sk.channel().close();
                        }
                    }
                    if(conent.length()>0){
                        for (SelectionKey key :selector.keys()) {
                            Channel c = key.channel();
                            if(c instanceof SocketChannel){
                                SocketChannel socketChannel = (SocketChannel) c;
                                socketChannel.write(StandardCharsets.UTF_8.encode(conent));
                            }
                        }
                    }
                }
            }
        }
    }

    public static  void main(String[] args) throws IOException {
        new NServer().init();
    }
}
