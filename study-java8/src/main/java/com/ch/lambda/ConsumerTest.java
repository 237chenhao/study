package com.ch.lambda;

import java.util.function.Consumer;

/**
 * Created by chenhao on 2017/10/12.
 */
public class ConsumerTest {

    public static void main(String[] args) {
        A a = new A();
        a.setA(1);
        Consumer<A> c1 = ConsumerTest::calculation;
        c1.andThen(ConsumerTest::calculation)
                .andThen(ConsumerTest::calculation)
                .accept(a);
        System.out.println(a.getA());
    }

    private static void calculation(A a){
        a.setA(1+a.getA());
    }

    private static class A{
        private int a;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }
}
