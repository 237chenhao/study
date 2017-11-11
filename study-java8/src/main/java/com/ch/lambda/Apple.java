package com.ch.lambda;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenhao on 2017/9/25.
 */
@ToString
public class Apple {
    private String color;

    private Integer weitht;

    public Apple(String color, Integer weitht) {
        this.color = color;
        this.weitht = weitht;
    }

    public static boolean isGreenApple(Apple a){
        return "green".equals(a.getColor());
    }
    public static boolean isHeavyApple(Apple a){
        return a.getWeitht() >150;
    }
    public static List<Apple> fileApples(List<Apple> apples,Predicate<Apple> p){
        List<Apple> r = new ArrayList<>();
        for (Apple a : apples){
            if(p.test(a)){
                r.add(a);
            }
        }
        return r;
    }

    public static boolean t(){
        return false;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getWeitht() {
        return weitht;
    }

    public void setWeitht(Integer weitht) {
        this.weitht = weitht;
    }
}
