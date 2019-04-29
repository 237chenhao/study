package com.study.other.jjwt;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * SnowFlake算法是Twitter设计的一个可以在分布式系统中生成唯一的ID的算法，
 * 它可以满足Twitter每秒上万条消息ID分配的请求，这些消息ID是唯一的且有大致的递增顺序。
 * @author cj-ch
 * @date 2018/12/6 下午6:04
 * 参考  http://www.wolfbe.com/detail/201611/381.html
 *      https://github.com/downgoon/snowflake/blob/master/docs/SnowflakeTutorial_zh_CN.md
 */
public class SnowFlake {
    /**
     * 起始的时间戳
     * 2018/12/5 0:0:0
     */
    private final static long START_STMP = 1543939200000L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数
    private final static long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws InterruptedException {
        gen();


    }

    private static void gen2() throws InterruptedException {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        long num = snowFlake.nextId();
        String binaryString = Long.toBinaryString(num);
        System.out.println(num + " - " + binaryString + " - " + binaryString.length());
        System.out.println(Long.valueOf(binaryString,2));

        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println("\n\n");
        num = snowFlake.nextId();
        binaryString = Long.toBinaryString(num);
        System.out.println(num + " - " + binaryString + " - " + binaryString.length());
        System.out.println(Long.valueOf(binaryString,2));
    }

    private static void gen() {
        System.out.println(MAX_DATACENTER_NUM);
        System.out.println(MAX_MACHINE_NUM);
        Object obj = new Object();
        SnowFlake snowFlake = new SnowFlake(2, 3);
        Set<String> sets = new HashSet<>(10000);
        AtomicLong atom = new AtomicLong(0);

        long in = System.currentTimeMillis();
        Runnable runnable = () -> {
            for (;;){
                long num = snowFlake.nextId();
                String numstr = String.valueOf(num);
                if(sets.contains(numstr)){
                    System.out.println("已经存在:" + numstr);
                }else{
                    synchronized (obj){
                        sets.add(String.valueOf(numstr));
                    }
                }

                long l;
                if((l = atom.incrementAndGet()) >= 2000000L){
                    System.out.println(Thread.currentThread().getName()+" , "+ l);
                    System.out.println( "use time: " + (System.currentTimeMillis() - in));
                    break;
                }
            }
        };

        int c = 5;
        while(c-- > 0){
            Thread thread = new Thread(runnable);
            thread.start();
//            thread.join();
        }
    }
}
