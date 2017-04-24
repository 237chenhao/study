package com.ch.study.enums;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        System.out.println(Integer.parseInt("ff",16));
        System.out.println(Integer.parseInt("00",16));
        System.out.println(0x0001|0x0020);
        System.out.println(Byte.parseByte("00101010",2));
        System.out.println(Integer.toBinaryString(-170));
        System.out.println(Integer.toBinaryString(170));
        System.out.println(4>>1);
        System.out.println(20>>1);
        List<String> list = new ArrayList<>();
        list.add("dddd");

        System.out.println(15+(15>>1));

    }
    public static <T> T[] values(Class<T> c){
        return c.getEnumConstants();
    }
}
