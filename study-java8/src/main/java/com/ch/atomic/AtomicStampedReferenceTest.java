package com.ch.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author cj-ch
 * @date 2018/10/29 下午5:43
 */
public class AtomicStampedReferenceTest {
    public static void main(String[] args) {
        AtomicStampedReference<Integer> atomic = new AtomicStampedReference<>(8,888);
        boolean b = atomic.compareAndSet(8, 999, 888, 999);
        System.out.println(b);
         b = atomic.compareAndSet(8, 999, 888, 999);
        System.out.println(b);
    }
}
