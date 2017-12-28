package com.ch.optional;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

/**
 * Created by chenhao on 2017/11/14.
 */
public class OptionalTest {

    private Person person;

    @Before
    public void b() {
        person = new Person();
        Car car = new Car();
        person.setCar(car);
    }

    @Test
    public void test1() {
        if (null != person) {
            Car car = person.getCar();
            if (null != car) {
                Insurance insurance = car.getInsurance();
                if (null != insurance) {
                    String name = insurance.getName();
                    System.out.println(name == null ? "默认值" : name);
                }
            }
        }

        System.out.println("------华丽的分割线------");

        Optional<Person> personOptional = Optional.of(person);
        Optional<String> stringOptional = personOptional
                .map(Person::getCar)
                .map(Car::getInsurance)
                .map(Insurance::getName);
        System.out.println(stringOptional.orElse("默认值"));
    }

    @Test
    public void test2() {

        String name = Optional.ofNullable(person)
                .filter(person1 -> person1.getAge() > 18)
                .map(Person::getCar)
                .map(Car::getInsurance)
                .map(Insurance::getName)
                .orElseGet(() -> {
                    //自己的复杂逻辑
                    return "UnKnown";
                });

        System.out.println(name);
    }


}
