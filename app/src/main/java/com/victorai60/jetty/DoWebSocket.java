package com.victorai60.jetty;

import org.eclipse.jetty.websocket.*;

import java.io.IOException;

class DoWebSocket implements WebSocket.OnTextMessage {
    private Connection mConnection;

    @Override
    public void onMessage(String data) {
        System.out.println("onMessage: " + data);
        if (mConnection != null) {
            try {
                mConnection.sendMessage("Hello " + data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onOpen(Connection connection) {
        mConnection = connection;
        WebSocketHandler.getBroadcast().add(this);
    }

    @Override
    public void onClose(int code, String message) {
        WebSocketHandler.getBroadcast().remove(this);
    }
}
