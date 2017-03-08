package com.ch.study.thread;

/**
 * Created by chenhao on 2017/2/15.
 */
public class DialSynch {
    private Object synch = new Object();
    public synchronized void f(){
        for(int i=0;i<5;i++){
            System.out.println("f()");
            Thread.yield();
        }
    }
    public  void g(){
        synchronized (synch){
            for(int i=0;i<5;i++){
                System.out.println("g()");
                Thread.yield();
            }
        }

    }

    public static void main(String[] args) {
        DialSynch dialSynch = new DialSynch();
        new Thread(() -> {
            dialSynch.g();
        }).start();
        dialSynch.f();
    }
}
