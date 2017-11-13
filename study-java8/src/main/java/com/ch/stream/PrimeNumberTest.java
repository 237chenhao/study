package com.ch.stream;

import com.ch.lambda.Predicate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by chenhao on 2017/10/27.
 * 求质数
 */
public class PrimeNumberTest {
    public static void main(String[] argstt) {
        int limit = 1_000_000;
        long a = test(PrimeNumberTest::my,limit);
        System.out.println("自定义收集器:\t"+a);

        long b = test(PrimeNumberTest::py,limit);
        System.out.println("工厂收集器:\t"+b);
        System.out.println("自定义收集器 比工厂收集器 性能提升:"+ (b-a)*100.0/b +"%");
    }
    private static long test(Consumer<Integer> consumer,Integer limit){
        long exeTime = Long.MAX_VALUE;
        for(int i=0;i<10;i++){
            long l1 = System.nanoTime();
            consumer.accept(limit);
            long t1 = System.nanoTime() - l1;
            if(t1 < exeTime){
                exeTime = t1;
            }
        }
        return exeTime;
    }

    private static void py(int r) {
        Map<Boolean, List<Integer>> collect2 = IntStream.rangeClosed(2, r)
                .boxed()
                .collect(Collectors.partitioningBy(PrimeNumberTest::isPrime));
//        System.out.println(collect2);
    }

    private static void my(int r) {
        Map<Boolean, List<Integer>> collect1 = IntStream.rangeClosed(2, r)
                .boxed()
                .collect(new PrimeNumbersCollector());
//        System.out.println(collect1);
    }

    public static boolean isPrime(int candicate){
        int t = (int) Math.sqrt(candicate);
        return IntStream.rangeClosed(2,t)
                .noneMatch(value -> candicate % value == 0);
    }

    public static boolean isPrime(List<Integer> primes,int candicate){
        int t = (int) Math.sqrt(candicate);
        return takeWhile(primes,t1 -> t1 <= t)
                .stream()
                .noneMatch(value -> candicate % value == 0);
    }

    public static <T> List<T> takeWhile(List<T> list, Predicate<T> p){
        int i  = 0;
        for(T t : list){
            if(!p.test(t)){
                return  list.subList(0,i);
            }
            i++;
        }
        return list;
    }

}
