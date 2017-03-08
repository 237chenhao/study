package com.ch.study.thread;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by chenhao on 2017/2/25.
 */
public class Sum {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int length=11;
        int[] arr = new int[length];
        for(int i=0;i<length;i++){
            arr[i]=i;
        }
        System.out.println(Arrays.toString(arr));
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        long in = System.nanoTime();
        Future<Integer> future = forkJoinPool.submit(new CalTask(arr,0,length-1));
        System.out.println(future.get());
        long out = System.nanoTime();
        System.out.println("use : "+(out-in));
        System.out.println("main");
        forkJoinPool.shutdown();
        System.out.println(Byte.MAX_VALUE+"=="+Byte.MIN_VALUE);
        System.out.println(Integer.parseInt("0111111111111111",2));
        System.out.println(Integer.parseInt("1111111111111111",2));
        System.out.println(Integer.toBinaryString(-65535));
    }
}

class CalTask extends RecursiveTask<Integer>{
    private static final int threshold = 3;
    private int[] arr;
    private int start;
    private int end;
    public CalTask(int[] arr,int start,int end){
        if(start>end || arr == null){
            throw new IllegalArgumentException("args error");
        }
        this.start=start;
        this.end=end;
        this.arr=arr;
    }
    @Override
    protected Integer compute() {
        int diff = end-start;
        int sum=0;
        if(diff<=threshold){
            for(;start<=end;start++){
                sum+=arr[start];
            }
        }else{
            int count = diff%threshold==0?diff/threshold:diff/threshold+1;
            for(int i=0;i<count;i++){
                int theEnd = start+(i+1)*threshold;
                sum+=new CalTask(arr,1+start+i*threshold,theEnd<end?theEnd:end).compute();
            }
        }
        return sum;
    }
}