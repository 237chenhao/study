package com.study.other.jjwt;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cj-ch
 * @date 2018/12/6 上午1:24
 */
public class Test1 {

    /**
     * (n - 1) & hash    n-长度  hash-因子
     * @param args
     * @throws InterruptedException
     */
    public static void main(String args[]) throws InterruptedException {
        long l = System.currentTimeMillis();
        int c = 1;
        while (c-- > 0){
            System.out.println("\n========================= " + c);
            gen();
            System.out.println("========================= end\n");
        }

        System.out.println("\n all use time:" + (System.currentTimeMillis() - l));
    }

    private static void gen() throws InterruptedException {
        Object obj = new Object();
        Set<String> sets = new HashSet<>(10000);
        AtomicLong atom = new AtomicLong(0);
        long in = System.currentTimeMillis();
        Runnable runnable = () -> {
            for(;;){
//                String num = UUID.randomUUID().toString();
                String num = RandomStringUtils.randomAlphanumeric(20);
                System.out.println(num);
                if(sets.contains(num)){
                    System.out.println("已经存在:" + num);
                }else{
                    synchronized (obj){
                        sets.add(num);
                    }
                }

                long l;
                if((l = atom.incrementAndGet()) >= 10L){
                    System.out.println(Thread.currentThread().getName()+" , "+ l);
                    System.out.println( "use time: " + (System.currentTimeMillis() - in));
                    break;
                }
            }
        };

        int c = 5;
        while(c-- > 0){
            Thread thread = new Thread(runnable);
            thread.start();
//            thread.join();
        }

    }


}
