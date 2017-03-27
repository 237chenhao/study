package com.ch.study.thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenhao on 2017/2/25.
 */
public class ForkJoinPoolDemo {
    public static void main(String[] args) throws InterruptedException {

        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.submit(new PrintTask(0,23));
        forkJoinPool.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("main will exit");
        forkJoinPool.shutdown();
    }
}

class PrintTask extends RecursiveAction{
    private static final int threshold=50;
    private final int start;
    private final int end;
    public PrintTask(int start,int end){
        if(start>end){
            throw new IllegalArgumentException("args error");
        }
        this.start = start;
        this.end = end;
    }
    @Override
    protected void compute() {
        int diff = end - start;
        if(diff<=threshold){
            for(int i=start;i<end;i++){
                System.out.println(Thread.currentThread().getName()+"打印："+i);
            }
        }else{
            int count = diff%threshold==0?diff/threshold:diff/threshold+1;
            for(int i=0;i<count;i++){
                int theEnd = start+(i+1)*threshold;
                PrintTask printTask = new PrintTask(start+i*threshold,theEnd<end?theEnd:end);
                printTask.fork();
            }
        }

    }
}
