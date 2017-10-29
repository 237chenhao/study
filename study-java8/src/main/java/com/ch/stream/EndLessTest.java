package com.ch.stream;

import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by admin on 2017/10/22.
 * 无限流测试
 */
public class EndLessTest {
    public static void main(String[] args) {
//        Stream.iterate(0,integer -> integer+2)
//                .limit(10)
//                .forEach(System.out::println);
        //斐波纳契
        //iterate
        Stream.iterate(new int[]{0,1},ints -> new int[]{ints[1],ints[0]+ints[1]})
                .limit(20)
                .map(ints -> ints[0])
                .forEach(integer -> System.out.print(integer + " "));
        System.out.println();
        //generate
        Stream.generate(Math::random)
                .limit(20)
                .forEach(d -> System.out.print(d + " "));
        System.out.println();
        IntSupplier intSupplier = new IntSupplier() {
            private int a=0;
            private int b=1;
            @Override
            public int getAsInt() {
                int t = a;
                a=b;
                b=a+t;
                return t;
            }
        };
        IntStream.generate(intSupplier)
                .limit(10)
                .forEach(i -> System.out.print(i+" "));
    }
}
