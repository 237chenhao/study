package com.study.other.netty;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

/**
 * @author cj-ch
 * @date 2019/4/29 上午9:07
 */
@Slf4j
public class MultiplexerTimeServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel servChannel;
    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            servChannel = ServerSocketChannel.open();
            servChannel.configureBlocking(false);
            servChannel.bind(new InetSocketAddress(port), 1024);
            servChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("the server is start in port:{}", port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key;
                while(it.hasNext()){
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        e.printStackTrace();
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                try {
                                    key.channel().close();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            // 新的连接请求
            if(key.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                // 建立连接后监听读
                sc.register(selector, SelectionKey.OP_READ);
                log.info("接收到新的客户端连接请求:{}", ssc.getLocalAddress());
            }
            // 监听到可读
            if(key.isReadable()){
                // 读取客户端发送的数据
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes > 0){
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "utf-8");
                    log.info("接收到客户端请求内容:{}", body);
                    body = StringUtils.deleteWhitespace(body);
                    String resp = "query_time".equalsIgnoreCase(body) ? (LocalDateTime.now().toString()) : "bad command";
                    doWrite(sc, resp);
                }else if(readBytes < 0){
                    // 对方关闭连接
                    log.info("客户端关闭链接:{} - {}", sc.getLocalAddress(), sc.getRemoteAddress());
                    key.cancel();
                    sc.close();

                }else {
                    // 读取到0字节,忽略
                    log.info("接收到客户端请求内容0字节");
                }
            }
        }
    }
    private void doWrite(SocketChannel socketChannel, String resp) throws IOException {
        if(resp != null && resp.trim().length() > 0){
            byte[] bytes = resp.getBytes("utf-8");
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);

        }
    }
}
