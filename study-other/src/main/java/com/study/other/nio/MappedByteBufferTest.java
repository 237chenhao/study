package com.study.other.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        File file = new File("d:/abc/a.txt");
        if(!file.exists()){
            file.createNewFile();
        }
        RandomAccessFile fileA = new RandomAccessFile(file, "rw");
        FileChannel channel = fileA.getChannel();
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        System.out.println((char)buffer.get() + " position=" + buffer.position());
        System.out.println((char)buffer.get() + " position=" + buffer.position());

        buffer.put((byte)'G');
        channel.close();
        fileA.close();
    }
}
