package com.ch.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created by admin on 2017/10/21.
 */
public class StreamReduceTest {
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
        int intValue = menu
                .stream()
                .map(dish -> dish.getCalories())
                .reduce(0, Integer::sum)
                .intValue();
        System.out.println(intValue);
        Optional<Integer> min = menu
                .stream()
                .map(dish -> dish.getCalories())
                .reduce(Integer::min);
        System.out.println(min.get());
    }
}
