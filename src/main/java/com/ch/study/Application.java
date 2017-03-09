package com.ch.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenhao on 2017/3/8.
 */
@RestController
@EnableAutoConfiguration
@ComponentScan
public class Application {
    @RequestMapping("/")
    public String home(){
        return "hello word!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
