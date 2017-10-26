package com.ch.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by chenhao on 2017/10/20.
 */
public class DishTest {

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

        Map<Dish.Type, Set<Integer>> collect = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.mapping(o -> {
                            if (o.getCalories() < 400) {
                                return 1;
                            } else if (o.getCalories() < 700) {
                                return 2;
                            }
                            return 3;
                        }, Collectors.toCollection(HashSet::new))));

        System.out.println(collect);
    }
}
