package com.ch.stream.parallel;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Created by chenhao on 2017/11/12.
 */
@Slf4j
public class StreamParallelTest {

    @Test
    public void test1(){
        long t1 = System.nanoTime();
        long a = Stream.iterate(1L,integer -> integer+1L)
                .limit(10_000_000L)
                .reduce(0L,Long::sum);
        System.out.println(a);
        long u1 = System.nanoTime()-t1;
        System.out.println(u1);

        t1 = System.nanoTime();
        a = Stream.iterate(1L,integer -> integer+1L)
                .limit(10_000_000L)
                .parallel()
                .reduce(0L,Long::sum);
        System.out.println(a);
        long u2 = System.nanoTime()-t1;
        System.out.println(u2);

        System.out.println("并行流比顺序流 性能提升:"+(u1-u2)*100.0/u1 + "%");
        /**
         * 是不是很失望?
         * 主要因数是iterate() 方法不恰当
         */
    }

    @Test
    public void test2(){
        final long n = 10_000_000L;
        Function<Long,Long> f1 = o -> LongStream.rangeClosed(1L,n)
               .reduce(0L,Long::sum);

        Function<Long,Long> f2 = o -> LongStream.rangeClosed(1L,n)
                .parallel()
               .reduce(0L,Long::sum);

        long a = testSum(f1,n);
        long b = testSum(f2,n);
        log.info("顺序流用时:{},并行流用时:{},顺序流比并行流 性能提升:{}%",a,b,(a-b)*100.0/a);
    }

    public long testSum(Function<Long,Long> addr,long n){
        long fastest = Long.MAX_VALUE;
        for(int i=0;i<10;i++){
            long t = System.nanoTime();
            long sum = addr.apply(n);
            System.out.println("结果:" + sum);
            long u = (System.nanoTime() - t)/1_000;
            if(fastest > u){
                fastest = u;
            }
        }
        return fastest;
    }
}
