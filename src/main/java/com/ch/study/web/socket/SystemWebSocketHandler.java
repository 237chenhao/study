package com.ch.study.web.socket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by chenhao on 2017/3/6.
 */
public class SystemWebSocketHandler implements WebSocketHandler {
    @java.lang.Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {

    }

    @java.lang.Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    @java.lang.Override
    public void handleTransportError(WebSocketSession webSocketSession, java.lang.Throwable throwable) throws Exception {

    }

    @java.lang.Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    @java.lang.Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
