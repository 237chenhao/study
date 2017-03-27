package com.ch.study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 演示++操作不是原子性操作
 * Created by chenhao on 2017/2/15.
 */
public class SerialNumberChecker {
    private static final int SIZE=20;
    private static CircularSet set = new CircularSet(1000);
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    static class SeriaChecker implements Runnable{
        @Override
        public void run() {
            while(true){
                int serrial = SerialNumberGenerator.next();
                if(set.contains(serrial)){
                    System.out.println("重复的值 ："+serrial);
                    System.exit(0);
                }
                set.add(serrial);
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<SIZE;i++){
            executorService.execute(new SeriaChecker());
        }
        try {
            TimeUnit.SECONDS.sleep(10);
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

class SerialNumberGenerator{
    private static volatile int serialNumber = 0;
    public  static final int next(){
        return  ++serialNumber;
    }
}

class CircularSet{
    private int[] array;
    private int len;
    private int index=0;
    public CircularSet(int size){
        array = new int[size];
        len=size;
        for(int i=0;i<len;i++){
            array[i]=-1;
        }
    }
    public synchronized void add(int i){
        array[index]=i;
        index=(++index)%len;
    }
    public synchronized boolean contains(int a){
        for(int i=0;i<len;i++){
            if(array[i]==a){
                return true;
            }
        }
        return false;
    }
}
