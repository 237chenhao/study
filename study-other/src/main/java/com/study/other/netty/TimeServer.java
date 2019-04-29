package com.study.other.netty;

/**
 * @author cj-ch
 * @date 2019/4/29 上午9:06
 */
public class TimeServer {
    public static void main(String[] args) {
        MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(8010);
        new Thread(multiplexerTimeServer, "NIO-multiplexerTimeServer").start();
    }
}
