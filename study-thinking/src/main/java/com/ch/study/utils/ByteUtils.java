package com.ch.study.utils;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;

import java.util.Arrays;

/**
 * Created by chuangjiangx-chenhao on 2017/5/17.
 */
public class ByteUtils {

    static int bytes2int(byte[] bytes,int start,int len){
        int end =len+start;
        int sum=0;
        for (int i = start; i < end; i++) {
            int j = (((int)bytes[i])&0xff);
            j = j << (8*(--len));
            sum = sum + j;
        }
        return sum;
    }

    static byte[] int2Bytes(int value,int len){
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[len-1-i]= (byte) ((value>>(8*i)) & 0xff);
        }
        return bytes;
    }

    public static void main(String[] args) {
        byte[] bytes = new byte[]{0,1,0,1};
        System.out.println(bytes2int(bytes,0,4));

        System.out.println(Arrays.toString(int2Bytes(65537,4)));
    }

}
