package com.ch.study.thread.sysnc;

import javax.sound.midi.Soundbank;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chenhao on 2017/3/9.
 */
public class SynchronizationComparisons{
    static BaseLine baseLine = new BaseLine();
    static SysnchronizedTest sysnchronizedTest = new SysnchronizedTest();
    static LockTest lockTest = new LockTest();
    static AtomicTest atomicTest = new AtomicTest();
    static void test(){
        System.out.println("===========================================");
        System.out.println(String.format("%-12s : %13d","Cycles",Accumulator.cycles));
        baseLine.timedTest();
        sysnchronizedTest.timedTest();
        lockTest.timedTest();
        atomicTest.timedTest();
        Accumulator.report(sysnchronizedTest,baseLine);
        Accumulator.report(lockTest,baseLine);
        Accumulator.report(atomicTest,baseLine);
        Accumulator.report(sysnchronizedTest,lockTest);
        Accumulator.report(sysnchronizedTest,atomicTest);
        Accumulator.report(lockTest,atomicTest);
    }

    public static void main(String[] args) {
        int iterations=5;
        System.out.println("Warmup");
        baseLine.timedTest();
        for(int i =0;i<iterations;i++){
            test();
            Accumulator.cycles<<=1;
        }
        Accumulator.exec.shutdown();
    }
}
abstract class Accumulator {
    public static long cycles = 50000l;
    private static final int N=4;
    public static ExecutorService exec = Executors.newFixedThreadPool(N<<1);
    private static CyclicBarrier barrier = new CyclicBarrier((N<<1)+1);
    protected volatile int index=0;
    protected volatile long value=0;
    protected long duration=0;
    protected String id="error";
    protected static final int SIZE=100000;
    protected static int[] preLoaded = new int[SIZE];
    static {
        Random random = new Random(47);
        for(int i=0;i<SIZE;i++){
            preLoaded[i]=random.nextInt();
        }
    }
    public abstract void accumulate();
    public abstract long read();

    private class Modifier implements Runnable{
        @Override
        public void run() {
            for(long i=0;i<cycles;i++){
                accumulate();
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
    private class Reader implements Runnable{
        private volatile long value;
        @Override
        public void run() {
            for(long i=0;i<cycles;i++){
                value = read();
            }
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
    public void timedTest(){
        long start = System.nanoTime();
        for(int i =0;i<N;i++){
            exec.execute(new Modifier());
            exec.execute(new Reader());
        }
        try{
            barrier.await();
        } catch (Exception e){
            System.out.println(e);
        }
        duration = System.nanoTime() - start;
        System.out.println(String.format("%-13s: %13d",id,duration));
    }
    public static void report(Accumulator acc1,Accumulator acc2){
        System.out.println(String.format("%-22s: %.2f",acc1.id+"/"+acc2.id,(double)acc1.duration/(double)acc2.duration));
    }
}
class BaseLine extends Accumulator{
    {
        id="BaseLine";
    }
    @Override
    public void accumulate() {
        index++;
        if((index+10)>=SIZE){
            index=0;
        }
        value+=preLoaded[index];

    }

    @Override
    public long read() {
        return value;
    }
}
class SysnchronizedTest extends Accumulator{
    {
        id="sysnchronized";
    }
    @Override
    public synchronized void accumulate() {
        value+=preLoaded[index++];
        if(index>=SIZE){
            index=0;
        }
    }

    @Override
    public synchronized long read() {
        return value;
    }
}

class LockTest extends Accumulator{
    {
        id="Lock";
    }
    private Lock lock = new ReentrantLock();
    @Override
    public  void accumulate() {
        try {
            lock.lock();
            value+=preLoaded[index++];
            if(index>=SIZE){
                index=0;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public  long read() {
        try {
            lock.lock();
            return value;
        } finally {
            lock.unlock();
        }
    }
}
class AtomicTest extends Accumulator{
    {
        id="Atomic";
    }
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private AtomicLong atomicLong = new AtomicLong(0);
    @Override
    public void accumulate() {
        int i = atomicInteger.getAndIncrement();
        if(i>=SIZE){
            atomicInteger.set(0);
            i=0;
        }
        atomicLong.getAndAdd(preLoaded[i]);

    }

    @Override
    public long read() {
        return atomicLong.get();
    }
}
