package com.ch.study.web.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by chenhao on 2017/3/6.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        System.out.println("注册WebSocketHandler");
        webSocketHandlerRegistry
                .addHandler(systemWebSocketHandler(),"/websocket").addInterceptors(webSocketHandshake())
                .setAllowedOrigins("*");
//                .withSockJS();
    }
    @Bean
    public SystemWebSocketHandler systemWebSocketHandler(){
        return new SystemWebSocketHandler();
    }
    @Bean
    public WebSocketHandshake webSocketHandshake(){
        return new WebSocketHandshake();
    }
}
