package com.aispeech.client;

import java.util.UUID;

public class Starter {

    // 使用自己产品的相关参数替换下列参数。
    private static final String productId = "x";
    private static final String apiKey = "x";
    // 指定使用的第一路语言模型。可以使用如 comm/aicar/airobot/aihome 等。
    private static final String res = "comm";
    private static final String filename = "asr/asrtest.wav";

    public static void main(String args[]) {
        String url = String.format("ws://asr.dui.ai/runtime/v2/recognize?productId=%s&apikey=%s&res=%s", productId, apiKey, res);

        String requestParams = String.format("{\n" +
                 "    \"context\": {\n" +
                 "        \"productId\": \"%s\"\n" +
                 "    },\n" +
                 "    \"request\": {\n" +
                 "        \"requestId\": \"%s\",\n" +
                 "        \"audio\": {\n" +
                 "            \"audioType\": \"wav\",\n" +
                 "            \"sampleBytes\": 2,\n" +
                 "            \"sampleRate\": 16000,\n" +
                 "            \"channel\": 1,\n" +
                 "            \"compress\": \"raw\"\n" +
                 "        },\n" +
                 "        \"asr\": {\n" +
                 "            \"enableVAD\": true,\n" +
                 "            \"enableRealTimeFeedback\": false\n" +
                 "        }\n" +
                 "    }\n" +
                 "}", productId, UUID.randomUUID());

        AsrliteWebSocketClient asrliteWebSocketClientApp = new AsrliteWebSocketClient();
        asrliteWebSocketClientApp.doAsrWebSocketClient(url, requestParams, filename, 3200);
    }
}
