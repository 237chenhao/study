package com.ch.lambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by chenhao on 2017/9/25.
 */
public class AppleTest {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Apple> apples = new ArrayList<>();
        Apple a1 = new Apple("sf",56);
        Apple a2 = new Apple("green",156);
        Apple a3 = new Apple("yellow",150);
        Apple a4 = new Apple("blue",178);
        Apple a5 = new Apple("red",109);
        apples.addAll(Arrays.asList(a1,a2,a3,a4,a5));
        List<Apple> t;
        t = Apple.fileApples(apples,apple -> Apple.isGreenApple(apple));
        System.out.println(objectMapper.writeValueAsString(t));

        t = Apple.fileApples(apples,Apple::isHeavyApple);
        System.out.println(objectMapper.writeValueAsString(t));
    }
}
