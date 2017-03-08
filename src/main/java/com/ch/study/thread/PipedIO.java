package com.ch.study.thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenhao on 2017/2/20.
 */
public class PipedIO {
    public static void main(String[] args) throws InterruptedException {
        Sender sender = new Sender();
        Reader reader = new Reader(sender.getPipedWriter());
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(sender);
        executorService.execute(reader);
        TimeUnit.SECONDS.sleep(10);
        executorService.shutdownNow();
    }
}

class Sender implements Runnable{
    private PipedWriter pipedWriter = new PipedWriter();

    public PipedWriter getPipedWriter() {
        return pipedWriter;
    }

    @Override
    public void run() {
        try {
            while(true){
                for(char i='A';i<='z';i++){
                    pipedWriter.write(i);
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(1000,5000));
                }
            }
        } catch (IOException e) {
            System.out.println(e+"sender write ioexception");
        } catch (InterruptedException e) {
            System.out.println(e+"send sleep exception");
        }
    }
}

class Reader implements Runnable{
    private PipedReader pipedReader;
    public Reader(PipedWriter pipedWriter){
        try {
            pipedReader = new PipedReader(pipedWriter);
        } catch (IOException e) {
            System.out.println(e+"读取异常");
        }
    }
    @Override
    public void run() {
        try {
            while(true){
                System.out.println("read:"+(char)pipedReader.read() +",");
            }
        } catch (IOException e) {
            System.out.println(e+ "received read exception");
        }
    }
}