package com.ch.study.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenhao on 2017/2/24.
 */
public class DaemonThreadDemo {
    public static void main(String[] args) throws InterruptedException {
//        for(int i=0;i<8000;i++){
//            new Thread(new DaemonThread()).start();
//        }
        System.out.println(Runtime.getRuntime().availableProcessors());
        TimeUnit.SECONDS.sleep(2);
        System.out.println("main 退出");
    }
}

class DaemonThread implements Runnable{
    static  int counter=0;
    private  int id=++counter;
    static final Object b = new Object();
    private static final List<Integer> integers = new ArrayList<>(10000);
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
            Thread.yield();
            in();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public  void in(){
        synchronized (b){
            if(integers.contains(id)){
                System.out.println(id+" 已经存在");
                return;
            }
            integers.add(id);
        }
    }
}
