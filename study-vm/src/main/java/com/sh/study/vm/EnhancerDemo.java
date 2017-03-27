package com.sh.study.vm;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by chuangjiangx-chenhao on 2017/3/26.
 */
public class EnhancerDemo {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setUseCache(false);
        enhancer.setSuperclass(ProxyClass.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return methodProxy.invokeSuper(o,objects);
            }
        });
        Object obj =  enhancer.create();
        System.out.println(obj);
    }

}
class ProxyClass{
    public void test(){
        System.out.println("proxy test class");
    }
}