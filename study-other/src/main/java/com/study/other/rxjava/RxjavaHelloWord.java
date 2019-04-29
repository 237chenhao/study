package com.study.other.rxjava;

import io.reactivex.Flowable;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 * @author cj-ch
 * @date 2019/1/16 下午8:44
 */
public class RxjavaHelloWord {
    public static void main(String[] args) throws InterruptedException {
        Flowable.just("Hello word!").delay(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);

        /************/
        Flowable.range(0,3)
                .map(e -> e * e)
                .subscribe(System.out::println);

        /************/
        Observable.create(emitter -> {
            while(!emitter.isDisposed()){
                long l = System.currentTimeMillis();
                emitter.onNext(l);
                if(l % 2 == 0){
                    emitter.onError(new IllegalStateException("嘿嘿嘿"));
                }
            }
        })
                .subscribe(o -> {
                    System.out.println(o);
                    TimeUnit.MILLISECONDS.sleep(500);
                }, Throwable::printStackTrace);



        TimeUnit.SECONDS.sleep(3);

    }
}
