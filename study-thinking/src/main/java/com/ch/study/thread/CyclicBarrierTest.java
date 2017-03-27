package com.ch.study.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by chenhao on 2017/2/22.
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            private int count=0;
            @Override
            public void run() {
                System.out.println("******************************");
                if(++count>10){
                    System.exit(0);
                }
            }
        });
        for(int i=0;i<5;i++){
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!Thread.interrupted()){

                        try {
                            System.out.println(finalI +" 即将进入睡眠");
                            cyclicBarrier.await();
                            System.out.println(finalI+" 被唤醒");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
