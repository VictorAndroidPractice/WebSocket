package com.victorai60.jetty;

import org.eclipse.jetty.websocket.WebSocket;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.http.HttpServletRequest;

class WebSocketHandler extends org.eclipse.jetty.websocket.WebSocketHandler {
    private static ConcurrentLinkedQueue<WebSocket.OnTextMessage> broadcast = new ConcurrentLinkedQueue<WebSocket.OnTextMessage>();

    static ConcurrentLinkedQueue<WebSocket.OnTextMessage> getBroadcast() {
        return broadcast;
    }

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
        return new DoWebSocket();
    }
}
