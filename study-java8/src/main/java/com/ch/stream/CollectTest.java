package com.ch.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by chenhao on 2017/10/23.
 */
public class CollectTest {
    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork",false,800, Dish.Type.MEAT),
                new Dish("beef",false,700, Dish.Type.MEAT),
                new Dish("chicken",false,400, Dish.Type.MEAT),
                new Dish("french fries",true,530, Dish.Type.OTHER),
                new Dish("rice",true,350, Dish.Type.MEAT),
                new Dish("season fruit",true,120, Dish.Type.MEAT),
                new Dish("pizza",true,550, Dish.Type.MEAT),
                new Dish("prawns",false,300, Dish.Type.FISH),
                new Dish("salmon",false,450, Dish.Type.FISH)
        );
        IntSummaryStatistics collect = menu.stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(collect);

        String collect1 = menu.stream()
                .map(t -> t.getName())
                .collect(Collectors.joining(","));
        System.out.println(collect1);

        Integer collect2 = menu.stream()
                .collect(Collectors.reducing(0, Dish::getCalories, (integer, integer2) -> integer + integer2));
        System.out.println(collect2);
        Optional<Dish> collect3 = menu.stream()
                .collect(Collectors.reducing((o, o2) -> o.getCalories() > o2.getCalories() ? o : o2));
        System.out.println(collect3.get().getCalories());


        String name = "sdfdsf.sdf.jpg";
        System.out.println(name.substring(name.lastIndexOf(".")+1));
    }
}
