package com.ch.study.internert;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by chenhao on 2017/2/25.
 */
public class InetAddressDemo {
    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
        System.out.println(inetAddress.isReachable(5000));
        System.out.println(inetAddress.getHostAddress());
        System.out.println(inetAddress.getCanonicalHostName());

        inetAddress = InetAddress.getByAddress(new byte[]{127,0,0,1});
        System.out.println(inetAddress.isReachable(2000));
        System.out.println(inetAddress.getCanonicalHostName());

//        String url = "http://s1.music.126.net/download/pc/cloudmusicsetup_2_1_2[168028].exe";
//        String targetFile="D:/jdk/jdk8.exe";
//        new File("D:/jdk").mkdirs();
//        DownloadUtils downloadUtils = new DownloadUtils(url,targetFile,5);
//        downloadUtils.download();
//
//        while(true){
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            double rate = downloadUtils.getCompleteRate();
//            System.out.println(rate);
//            if(rate>=100){
//                break;
//            }
//        }
    }
}
