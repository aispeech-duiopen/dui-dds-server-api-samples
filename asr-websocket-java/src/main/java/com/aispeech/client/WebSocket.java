package com.aispeech.client;


import javax.websocket.*;

@ClientEndpoint
public class WebSocket {
    private static void print(Object obj) {
        System.out.println(obj.toString());
    }

    @OnOpen
    public void onOpen(Session session) {
        print("Connect to endpoint: " + session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(String message) {
        print(message);
        if (message.contains("\"eof\":1")) {
            onClose();
        }
    }

    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnClose
    public void onClose() {
        System.exit(0);
    }
}
