package com.ch.lambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.fabric.xmlrpc.base.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by chenhao on 2017/9/25.
 */
public class AppleTest {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Apple a1 = new Apple("sf",56);
        Apple a2 = new Apple("green",156);
        Apple a3 = new Apple("yellow",150);
        Apple a4 = new Apple("blue",178);
        Apple a5 = new Apple("red",109);

        List<Apple> apples = Arrays.asList(a1,a2,a3,a4,a5);
        System.out.println("行为参数化，方法引用，Predicate接口，Consumer接口演示");
        List<Apple> rList = filter(apples,ArrayList<Apple>::new,apple -> apple.getWeitht() > 120 );
        Consumer<Apple> consumer = System.out::println;
        for (Apple a : rList){
            consumer.accept(a);
        }

        System.out.println("\nFunction接口演示");
        List<String> stringList = convert(apples, ArrayList<String>::new, apple -> apple.getColor());
        System.out.println(stringList);
    }

    /**
     * 筛选
     * @param list 筛选集合
     * @param supplier  获得存放筛选结果集
     * @param p     筛选行为
     * @param <T> 筛选对象
     * @return
     */
    static <T> List<T> filter(List<T> list,Supplier<List<T>> supplier,Predicate<T> p){
        List<T> rlist = supplier.get();
        for(T t : list){
            if(p.test(t)){
                rlist.add(t);
            }
        }
        return rlist;
    }

    /**
     * 转换
     * @param list 转换集合
     * @param supplier  获得存放转换结果集
     * @param f 转换行为
     * @param <T>   转换对象
     * @param <R>   目标对象
     * @return
     */
    static <T,R> List<R> convert(List<T> list, Supplier<List<R>> supplier, Function<T,R> f){
        List<R> rlist = supplier.get();
        for(T t : list){
            rlist.add(f.apply(t));
        }
        return rlist;
    }
}
