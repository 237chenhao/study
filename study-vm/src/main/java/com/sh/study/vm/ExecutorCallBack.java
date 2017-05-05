package com.sh.study.vm;

/**
 * Created by chenhao on 2016/11/28.
 */
public interface ExecutorCallBack {
    /**
     *
     * @param result 执行结果
     * @param success 是否执行成功
     * @param remark 备注
     */
    void process(Object result, boolean success, String remark);
}
