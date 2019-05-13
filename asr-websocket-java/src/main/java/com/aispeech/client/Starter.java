package com.aispeech.client;

public class Starter {
    private static void doAsrWebSocketClient(String requestData, String filePath, String authType, String authParams, Integer step) {
        AsrliteWebSocketClient asrliteWebSocketClientApp = new AsrliteWebSocketClient();
        asrliteWebSocketClientApp.doAsrWebSocketClient(requestData, filePath, authType, authParams, step);
    }

    public static void main(String args[]) {
        //doAsrWebSocketClient(asrWebSocketrequestData, fileName, authType, authParams, step);
    }
}
