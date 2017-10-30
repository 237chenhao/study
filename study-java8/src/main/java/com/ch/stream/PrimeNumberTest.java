package com.ch.stream;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by chenhao on 2017/10/27.
 * 求质数
 */
public class PrimeNumberTest {
    public static void main(String[] argstt) {
        Map<Boolean, Set<Integer>> collect = IntStream.range(2, 100).boxed()
                .collect(Collectors.groupingBy(o -> isPrime(o), Collectors.toSet()));
        System.out.println(collect);
    }
    public static boolean isPrime(int candicate){
        int t = (int) Math.sqrt(candicate);
        return IntStream.rangeClosed(2,t)
                .noneMatch(value -> candicate % value == 0);
    }
}
