package com.study.other.socket;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class ByteBufferTest {
    public static void main(String[] args) {
//        test1();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.putChar('是');
        byteBuffer.putChar('发');
        byteBuffer.putChar('吧');
        byteBuffer.putChar('吧');
        byteBuffer.putChar('吧');
//        byteBuffer.putChar('吧');

        byteBuffer.slice();

    }

    private static void test1() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put((byte)2);
        byteBuffer.put((byte)3);
        byteBuffer.put((byte)4);
        System.out.println("position:" + byteBuffer.position() + "\tlimit:" + byteBuffer.limit());
        System.out.println(byteBuffer.get());
        byteBuffer.flip();
        System.out.println(byteBuffer.get());
        System.out.println(byteBuffer.get());
        System.out.println(byteBuffer.get());
//        byteBuffer.limit(2);
//        System.out.println(byteBuffer.get());
        CharBuffer charBuffer = CharBuffer.allocate(5);
        charBuffer.put("啊的才吧䵷");
        charBuffer.flip();
        System.out.println(charBuffer.get(4));
    }
}
