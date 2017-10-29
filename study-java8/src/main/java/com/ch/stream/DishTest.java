package com.ch.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by chenhao on 2017/10/20.
 */
public class DishTest {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork",false,800, Dish.Type.MEAT),
                new Dish("pork",true,300, Dish.Type.FISH),
                new Dish("beef",false,700, Dish.Type.MEAT),
                new Dish("chicken",false,400, Dish.Type.MEAT),
                new Dish("french fries",true,530, Dish.Type.OTHER),
                new Dish("rice",true,350, Dish.Type.MEAT),
                new Dish("season fruit",true,120, Dish.Type.MEAT),
                new Dish("pizza",true,550, Dish.Type.MEAT),
                new Dish("prawns",false,300, Dish.Type.FISH),
                new Dish("salmon",false,450, Dish.Type.FISH)
        );
        List<Integer> collect = menu.
                stream()
                .filter(dish -> dish.getCalories() > 100)
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());

        System.out.println(collect);

        List<Integer> int1 = Arrays.asList(1,2);
        List<Integer> int2 = Arrays.asList(3,4,5);
        List<int[]> collect1 = int1
                .stream()
                .flatMap(integer ->
                        int2
                                .stream()
                                .filter(integer1 -> (integer+integer1)%3==0)
                                .map(integer1 -> new int[]{integer, integer1})
                )
                .collect(Collectors.toList());
        System.out.println(collect1);

        if(menu.stream().anyMatch(Dish::isVegetarian)){
            System.out.println("you素菜");
        }

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
