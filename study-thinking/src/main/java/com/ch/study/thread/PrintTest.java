package com.ch.study.thread;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author cj-chenhao on 2020-01-14 18:34
 * 实现3个线程循环有顺序的打印
 */
@Slf4j
public class PrintTest {
    public static void main(String[] args) {
        // A > B > c
        int count = 10;
        while(--count >=0){
            final Print a = new Print("A", null);
            final Print b = new Print("B", a);
            final Print c = new Print("C", b);
            a.start();
            b.start();
            c.start();
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Setter
    public static class Print extends Thread{
        private String text;
        private Thread t;

        public Print(String text, Thread t) {
            this.text = text;
            this.t = t;
        }

        @Override
        public void run() {
//            try {
//                if(t != null){
//                    t.join();
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            synchronized (t){
                log.info("{}", text);
            }
        }
    }

    public static class Lock extends AbstractQueuedSynchronizer{

    }
}
