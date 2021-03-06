package com.ch.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by chenhao on 2017/10/18.
 */
public class LambdaTest {

    public static void main(String[] args) {
        Function<String,Integer> stringIntegerFunction = Integer::valueOf;

        System.out.println(stringIntegerFunction.apply("88"));

        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.contains("d");
        BiPredicate<List<String>,String> biPredicate = List::contains;

        System.out.println(biPredicate.test(list,"sd"));

        String[] strs = {"hello","word"};
        Object[] objects = Stream.of(strs)
                .flatMap(s -> Stream.of(s.split("")))
                .distinct()
                .toArray();
        System.out.println(Arrays.toString(objects));

    }

    private static class A{
        private String a;
        public void consumer(String bb){
            System.out.println(bb);
        }
    }
}
