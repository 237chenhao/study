package com.sh.study.vm.thread;

import com.sh.study.vm.ExecutorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Created by chuangjiangx-chenhao on 2017/5/5.
 */
public class ThreadTest {
    static class MyThreadFactory implements ThreadFactory {
        private final AtomicInteger count = new AtomicInteger(0);
        private final String prefixName;

        public MyThreadFactory(String prefixName) {
            this.prefixName = prefixName;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(prefixName + "-" + count.incrementAndGet());
            return thread;
        }
    }
    static class Worker extends AbstractQueuedSynchronizer implements Runnable{
        private static final long serialVersionUID = 6138294804551838833L;
        static final MyThreadFactory myThreadFactory = new MyThreadFactory("exec--");
        /** Thread this worker is running in.  Null if factory fails. */
        final Thread thread;
        /** Initial task to run.  Possibly null. */
        Runnable firstTask;

        public Worker(Runnable firstTask) {
            setState(-1); // inhibit interrupts until runWorker
            this.firstTask = firstTask;
            this.thread = myThreadFactory.newThread(this);
        }

        @Override
        public void run() {
            firstTask.run();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        List<Worker> list = new ArrayList<>();
        final int[] i = {0};
        while(++i[0]<10){
            Worker w1 = new Worker(() -> {
                System.out.println(i[0]);
                int k=0;
//                while(true) {
//
//                    k++;
//                    if (k % 50000000 == 0) {
//                        k = 0;
//                        System.out.println(Thread.currentThread().getName());
//
//                    }
//                }
            });
            w1.thread.start();
            list.add(w1);
        }
        TimeUnit.SECONDS.sleep(10);
//        System.out.println("中断");
//        list.forEach(worker -> {
//            worker.thread.interrupt();
//        });
        TimeUnit.SECONDS.sleep(10);
        System.out.println("end");
    }
}
