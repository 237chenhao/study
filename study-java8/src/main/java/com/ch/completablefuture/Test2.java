package com.ch.completablefuture;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author cj-chenhao on 2019-11-15 09:26
 */
public class Test2 {
    /**
     * thenApply:
     *      当前阶段正常完成以后执行，而且当前阶段的执行的结果会作为下一阶段的输入参数。thenApplyAsync默认是异步执行的。这里所谓的异步指的是不在当前线程内执行。
     * thenAccept与thenRun
     *      可以看到，thenAccept和thenRun都是无返回值的。如果说thenApply是不停的输入输出的进行生产，那么thenAccept和thenRun就是在进行消耗。它们是整个计算的最后两个阶段。
     *      同样是执行指定的动作，同样是消耗，二者也有区别：
     *          thenAccept接收上一阶段的输出作为本阶段的输入
     *          thenRun根本不关心前一阶段的输出，根本不不关心前一阶段的计算结果，因为它不需要输入参数
     */
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() ->  1)
                .thenApply(integer -> {
                    System.out.println("thenApply1");
                    String comsume = comsume(integer);
                    System.out.println("thenApply1: " + comsume);
                    return comsume;
                })
                .thenApply(integer -> {
                    System.out.println("thenApply2");
                    String comsume = comsume(integer);
                    System.out.println("thenApply2: " + comsume);
                    return comsume;
                })
                .thenCombine(CompletableFuture.completedFuture("Java"), (s, s2) -> {
                    System.out.println("thenCombine1: " + s + " " + s2);
                    return s + s2;
                })
                .whenComplete((s, throwable) -> {
                    System.out.println("whenComplete " + comsume(s));
                });
    }

    public static final String comsume(Object obj)  {
        System.out.println(Thread.currentThread().getName() + " \tstart\t" + obj);
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String str = obj + RandomStringUtils.randomAlphanumeric(6);
        System.out.println(Thread.currentThread().getName() + " \tend\t" + obj);
        return str;
    }
}
