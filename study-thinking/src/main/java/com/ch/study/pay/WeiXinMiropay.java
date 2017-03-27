package com.ch.study.pay;

/**
 * Created by chuangjiangx-chenhao on 2017/3/22.
 */
public interface WeiXinMiropay<R extends RootResponse> extends Pay<R> {
    R weiXinMiropay();
}
