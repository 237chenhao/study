package com.ch.study.config;

import com.ch.study.web.socket.WebSocketConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by chenhao on 2017/3/6.
 */
@Configuration
@EnableWebMvc
@Import(value = {WebSocketConfig.class})
@ImportResource(value = {"classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class MvcConfig extends WebMvcConfigurerAdapter {
}
