package com.ch.study.thread;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chenhao on 2017/2/19.
 */
public class LockTest {
    public static void main(String[] args) {
        Barrel barrel = new Barrel(10);
        for(int i=0;i<10;i++){
            new Thread(new Producer(barrel)).start();
        }
        for(int i=0;i<5;i++){
            new Thread(new Customer(barrel)).start();
        }
    }
}

class Barrel{
    private int[] breads;
    private int len;
    private int index;
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();
    public Barrel(int size){
        breads = new int[size];
        this.len = size;
        this.index=-1;
    }
    public final void put(int p) throws InterruptedException {
        reentrantLock.lock();
        try {
            int count=0;
            while(true){
                count++;
                if(index<len-1){
                    breads[++index]=p;
//                    this.notify();
                    condition.signal();
                    System.out.println(Thread.currentThread().getName()+" 放进一个面包,还有"+(index+1)+"个面包,经过"+count+"次");
                    break;
                }else{
                    System.out.println(Thread.currentThread().getName()+" 满了,已有"+(index+1)+"个面包");
                    //this.wait();
                    condition.await();
                    System.out.println(Thread.currentThread().getName()+" 生产者被唤醒");
                }
            }
        }finally {
            reentrantLock.unlock();
        }
    }
    public final  int get() throws InterruptedException {
        reentrantLock.lock();
        try{
            int count=0;
            while(true){
                count++;
                if(index>=0){
                    int p = breads[index--];
//                    this.notify();
                    condition.signal();
                    System.out.println(Thread.currentThread().getName()+" 拿出一个面包，还有"+(index+1)+"个面包,经过"+count+"次");
                    return p;
                }else{
                    System.out.println(Thread.currentThread().getName()+" 没有面包了,还有"+(index+1)+"个面包");
//                    this.wait();
                    condition.await();
                    System.out.println(Thread.currentThread().getName()+" 被唤醒");
                }
            }
        }finally {
            reentrantLock.unlock();
        }
    }
}

class Producer  implements Runnable{
    private static int count=0;
    private int number = ++count;
    private Barrel barrel;
    public Producer(Barrel barrel){
        this.barrel = barrel;
    }
    public void run() {
        Thread.currentThread().setName("生产者"+number+"号");
        while (true){
            try {
                barrel.put(1);
                int sleep = ThreadLocalRandom.current().nextInt(100,1000);
                TimeUnit.MILLISECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Customer implements Runnable{
    private static int count=0;
    private int number = ++count;
    private Barrel barrel;
    public Customer(Barrel barrel){
        this.barrel = barrel;
    }
    public void run() {
        Thread.currentThread().setName("消费者"+number+"号");
        while(true){
            try {
                barrel.get();
                int sleep = ThreadLocalRandom.current().nextInt(100,1000);
                TimeUnit.MILLISECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
