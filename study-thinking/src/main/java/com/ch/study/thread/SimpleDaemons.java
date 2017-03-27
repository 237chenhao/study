package com.ch.study.thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by chenhao on 2017/2/10.
 */
public class SimpleDaemons {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Run());
        thread.start();
        System.out.println("main finish");
       // TimeUnit.SECONDS.sleep(8);

    }

   class ABC{
       private String a;
   }
}

class Run implements Runnable{
    @Override
    public void run() {
        Thread thread = new Thread(new Run2());
       // thread.setDaemon(true);
        thread.start();
        System.out.println("*run**");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("*run is exit*");
    }
}

class Run2 implements Runnable{
    @Override
    public void run() {
        while(true){
            System.out.println("==Run2===");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}