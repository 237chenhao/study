package com.ch.study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by chenhao on 2017/2/13.
 */
public class UncaughtException implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("uncaughtException thread:" + t);
        System.out.println("uncaughtException caught"+e);
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactory1());
        executorService.execute(new ExceptionThread());
    }
}

class ExceptionThread implements  Runnable{

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        System.out.println("run:"+thread);
        System.out.println("thread.getUncaughtExceptionHandler():"+thread.getUncaughtExceptionHandler());
        throw  new RuntimeException("a");
    }
}

class ThreadFactory1 implements ThreadFactory{
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setUncaughtExceptionHandler(new UncaughtException());
        System.out.println("at ThreadFactory1,thread.getUncaughtExceptionHandler():"+thread.getUncaughtExceptionHandler());
        return thread;
    }
}
