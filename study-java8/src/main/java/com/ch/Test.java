package com.ch;

/**
 * @author cj-ch
 * @date 2018/8/16 上午11:36
 */
public class Test {
    public static void main(String[] args) {
        boolean is = false;
        Integer wx = new Integer(1);
        if (wx != null) {
            is = 1 == wx || 13 == wx;
            if (!is) {
                System.out.println(String.format("商户ID:%1$d,微信支付配置的渠道ID是:{%2$d},无法进入H5C扫B支付+核销", 171, wx));
            }
        }
    }
}
