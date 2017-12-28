package com.ch.datetime;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * Created by chenhao on 2017/11/15.
 */
public class DateTimeTest {

    @Test
    public void test1(){
        LocalDate localDate = LocalDate.now();
        //2017-11-15
        System.out.println(localDate);

        //获取指定日期
        localDate = LocalDate.of(2017,12,12);
        //2017-12-12
        System.out.println(localDate);

        LocalDate newDate = localDate
                .plus(1, ChronoUnit.DAYS)
                .minus(1, ChronoUnit.MONTHS);

        //2017-11-12
        System.out.println(newDate);
    }

    @Test
    public void test2(){
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        LocalTime newTime = localTime.withHour(12)
                .withMinute(12)
                .withSecond(12);
        System.out.println(newTime);
    }

    @Test
    public void test3(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime newTime = now.withYear(2017)
                                .withMonth(9)
                                .withDayOfMonth(9)
                                .withHour(9)
                                .withMinute(9)
                                .withSecond(9);
        System.out.println(newTime);

        String timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(newTime);
        System.out.println(timeFormatter);
    }

    @Test
    public void test4(){
        Instant now = Instant.now();
        //毫秒
        int milli = now.get(ChronoField.MILLI_OF_SECOND);
        System.out.println(milli);
        //微秒
        int micro = now.get(ChronoField.MICRO_OF_SECOND);
        System.out.println(micro);
        //纳秒
        int nano = now.get(ChronoField.NANO_OF_SECOND);
        System.out.println(nano);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());
        System.out.println(localDateTime);
    }

    @Test
    public void test5(){
        Instant instant1 = Instant.now();
        Instant instant2 = instant1.plus(5,ChronoUnit.SECONDS);

        Duration duration = Duration.between(instant1,instant2);
        //两个时间差
        System.out.printf("相差 %d 秒 \n",duration.getSeconds());


        LocalDate time1 = LocalDate.now();
        LocalDate time2 = LocalDate.of(2018,Month.NOVEMBER,22);

        Period p = Period.between(time1,time2);
        System.out.printf("相差 : %d 年 %d 月 %d 日", p.getYears(), p.getMonths(), p.getDays());
    }


}
