package com.ch.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author cj-ch
 * @date 2018/6/22 上午9:07
 */
public class Test {
    static List<String> shops = Arrays.asList("A", "B", "C", "D","E");
    static ExecutorService executorService = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });
    public static void main(String[] args) {


        final String productName = "西瓜";

        long start = System.currentTimeMillis();

        List<CompletableFuture<Double>> completableFutureList = shops.stream()
                .map(s -> CompletableFuture.supplyAsync(() -> findPrices(s, productName)))
                .collect(Collectors.toList());
        System.out.println("start ...");
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]))
                .join();
        System.out.println("end ...");
//        stream(productName);
//        completable(productName);

        long end = System.currentTimeMillis();
        System.out.println(String.format("%s\t用时:%d", Thread.currentThread().getName(),end-start));
    }

    static void stream(String productName){
        List<Double> collect = shops.parallelStream()
                .map(s -> findPrices(s, productName))
                .collect(Collectors.toList());
    }
    static void completable(String productName){
        List<CompletableFuture<Double>> collect1 = shops.stream()
                .map(s ->
                        CompletableFuture.supplyAsync(() -> findPrices(s, productName),executorService)
                ).collect(Collectors.toList());
        List<Double> collect2 = collect1.stream()
                .map(s -> s.join())
                .collect(Collectors.toList());
    }

    static double findPrices(String shopName,String productName){
        System.out.println(Thread.currentThread().getName() + "查询商店:"+shopName+" 产品:"+productName+" 的价格");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "查询商店:"+shopName+" 产品:"+productName+" 的价格,查询完了");
        return  new Random().nextDouble()*100;

    }
}
