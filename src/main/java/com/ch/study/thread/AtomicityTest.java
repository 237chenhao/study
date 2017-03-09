package com.ch.study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chenhao on 2017/2/14.
 */
public class AtomicityTest implements Runnable {
    private int i =0;
    public int getValue(){return i;}
    private synchronized void evenIncrement(){i++;i++;}

    @Override
    public void run() {
        while(true){
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        System.out.println(2<<2);
        /*ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicityTest atomicityTest = new AtomicityTest();
        executorService.execute(atomicityTest);
        while(true){
            int val = atomicityTest.getValue();
            if(val%2!=0){
                System.out.println(val);
                System.exit(0);
            }
        }*/
    }
}
