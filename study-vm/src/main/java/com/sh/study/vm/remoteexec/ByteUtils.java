package com.sh.study.vm.remoteexec;

/**
 * Created by chuangjiangx-chenhao on 2017/5/11.
 * 数组处理工具
 */
public class ByteUtils {
    public static int bytes2int(byte[] b,int start,int len){
        int sum=0;
        int end = start+len;
        for (int i = start; i < end; i++) {
            int n = ((int)b[i]) & 0xFF;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }
    public static byte[] int2Bytes(int value,int len){
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len -i - 1] =(byte)((value >> 8 * i) & 0xFF);
        }
        return b;
    }
    public static String bytes2String(byte[] b,int start,int len){
        return new String(b,start,len);
    }
    public static byte[] string2Bytes(String str){
        return str.getBytes();
    }
    public static byte[] bytesReplace(byte[] originalBytes,int offset,int len,byte[] replaceBytes){
        byte[] newBytes = new byte[originalBytes.length +(replaceBytes.length - len)];
        System.arraycopy(originalBytes,0,newBytes,0,offset);
        System.arraycopy(replaceBytes,0,newBytes,offset,replaceBytes.length);
        System.arraycopy(originalBytes,offset+len,newBytes,offset+replaceBytes.length,
                originalBytes.length-offset-len);
        return newBytes;
    }
}
