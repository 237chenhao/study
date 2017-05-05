package com.sh.study.vm.exec;

import java.lang.invoke.*;

/**
 * Created by chuangjiangx-chenhao on 2017/5/5.
 */
public class InvokeDynamicTest {

    public static void testMethod(String str){
        System.out.println("hello string:"+str);
    }
    public static CallSite bootstrapMethod(MethodHandles.Lookup lookup, String name, MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
        return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class,name,methodType));
    }

    private static MethodType mt_bootstrapMethod(){
        return MethodType.fromMethodDescriptorString(
                "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;"
                ,null);
    }
    private static MethodHandle mh_bootstrapMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup()
                .findStatic(InvokeDynamicTest.class,"bootstrapMethod",mt_bootstrapMethod());
    }

    private static MethodHandle indy_bootstrapMethod() throws Throwable {
        CallSite callSite = (CallSite) mh_bootstrapMethod()
                .invokeWithArguments(MethodHandles.lookup(),
                        "testMethod",
                        MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V",null));
        return callSite.dynamicInvoker();
    }

    public static void main(String[] args) throws Throwable {
        indy_bootstrapMethod().invokeExact("www你这么复杂,我看不懂");
    }
}
