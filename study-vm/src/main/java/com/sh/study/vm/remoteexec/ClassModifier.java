package com.sh.study.vm.remoteexec;

/**
 * Created by chuangjiangx-chenhao on 2017/5/11.
 * 修改Class文件,暂时只提供修改常量池常量的功能
 */
public class ClassModifier {
    /**
     * Class文件常量池的偏移量
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;
    /**
     * CONSTANT_UTF8_INFO 常量的tag标志
     */
    private static final int CONSTANT_Utf8_info = 1;

    /**
     * 常量池中11种常量所占的长度,CONSTANT_UTF8_INFO型常量除外,因为它不是定长的
     */
    private static final int[] CONSTANT_ITEM_LENGTH ={-1,-1,-1,5,5,9,9,3,3,5,5,5,5};

    private static final int u1=1;

    private static final int u2=2;

    private byte[] classByte;

    public ClassModifier(byte[] classByte) {
        this.classByte = classByte;
    }

    /**
     * 修改常量池中CONSTANT_Utf8_info常量的内容
     * @param oldStr 修改前的字符串
     * @param newStr 修改后的字符串
     * @return 修改结果
     */
    public byte[] modifyUTF8Constant(String oldStr,String newStr){
//        int cpc =
        return null;
    }
    public int getConstantPoolCount(){
        return 0;
    }
}
