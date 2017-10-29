package com.ch.stream;

import org.codehaus.jackson.map.util.Comparators;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by admin on 2017/10/21.
 */
public class TradeTest {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul","Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,100),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2012,700),
                new Transaction(alan,2012,950)
        );
        //1
        List<Transaction> collect = transactions.stream()
                .filter(transaction -> 2011 == transaction.getYear())
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println("找出2011年发生的所有交易，并且按交易额排序（从低到高）\n"+collect);
        //2
        List<String> collect1 = transactions.stream()
                .map(transaction -> transaction.trader.city)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("交易员都在哪些不同的城市工作过？\n"+collect1);
        //3
        List<Trader> collect2 = transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.trader.city))
                .map(t -> t.trader)
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println("查找所有来自剑桥的交易员，并且按姓名排序\n"+collect2);
        //4
        String collect3 = transactions.stream()
                .map(transaction -> transaction.trader.name)
                .distinct()
                .sorted()
                .reduce("",(s, s2) -> s+","+s2);
        System.out.println("返回所有的交易员的姓名字符串，按字母顺序排序\n"+collect3);
        //5
        boolean any = transactions.stream()
                .anyMatch(transaction -> "Milan".equals(transaction.trader.city));
        System.out.println("有没有交易员是在米兰工作的？\n"+(any ? "有":"没有"));
        //6
        System.out.println("打印生活在剑桥的交易员的所有交易额");
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.trader.city))
                .map(t -> t.value)
                .forEach(System.out::println);
        //7
        OptionalInt max = transactions.stream()
                .mapToInt(t -> t.value)
                .max();
        System.out.println("所有交易额中，最高的交易额是：\n"+max.orElse(0));
        //8
        Optional<Transaction> first = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
        System.out.println("找到交易额最小的交易：\n"+first);

        //9
        Stream.of(transactions.stream()
                .collect(Collectors.groupingBy(o -> o.value > 500)))
                .flatMap(booleanListMap -> {
                    booleanListMap.values().stream().map(transactions1 -> transactions1.)
                })

    }



    public static class Trader{
        private final String name;
        private final String city;

        public Trader(String name, String city) {
            this.name = name;
            this.city = city;
        }
        public String getName() {
            return name;
        }
        public String getCity() {
            return city;
        }

        @Override
        public String toString() {
            return "[name="+name+",city="+city+"]";
        }
    }
    public static class Transaction{
        private final Trader trader;
        private final int year;
        private final int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return trader;
        }

        public int getYear() {
            return year;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "[trader="+trader+",year="+year+",value="+value+"]";
        }
    }
}
