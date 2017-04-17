package com.ch.study.enums;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

/**
 * Created by chuangjiangx-chenhao on 2017/4/14.
 */
public enum ActivityEnum {
    AAA,BBB,CCC,DDD;


    public static void main(String[] args) {
        Random random = new Random(47);
        ActivityEnum[] activityEnums = values(ActivityEnum.class);
        int len = activityEnums.length;
        int a=20;
        while(a-->0){
            ActivityEnum.class.getEnumConstants();
            System.out.println(activityEnums[random.nextInt(len)]);
        }
        System.out.println(Integer.parseInt("000a",16));
        System.out.println(Integer.parseInt("02",16));
        System.out.println(Integer.parseInt("01",16));
        System.out.println(Integer.parseInt("001D",16));
        String hex="6F72672F66656E6978736F66742F636C617A7A2F54657374436C617373";
        len = hex.length();
        for(int i =0;i<len;i+=2){
            char c = (char)Byte.parseByte(hex.substring(i,i+2),16);
            System.out.print(c);
        }
        System.out.println();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(date));
        Instant instant =Instant.ofEpochMilli(date.getTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        System.out.println(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));

        long t = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()).plusYears(1)
                .toInstant(ZoneOffset.MIN).toEpochMilli();
        System.out.println(simpleDateFormat.format(new Date(t)));

        System.out.println(Integer.parseInt("F0",16));
    }
    public static <T> T[] values(Class<T> c){
        return c.getEnumConstants();
    }
}
