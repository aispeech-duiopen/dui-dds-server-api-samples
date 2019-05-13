package com.aispeech.client;

public class Starter {
    private static void doAsrHttpClient(String requestData, String fileName, String authType, String authParams) {
        AsrliteHttpClient asrliteHttpClient = new AsrliteHttpClient();
        asrliteHttpClient.doAsrHttpClient(requestData, fileName, authType, authParams);
    }

    public static void main(String args[]) {
        //doAsrHttpClient(asrHttprequestData, filename, authType, authParams);
    }
}
