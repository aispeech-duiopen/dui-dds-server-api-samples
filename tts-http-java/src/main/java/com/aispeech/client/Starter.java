package com.aispeech.client;

import java.util.UUID;

public class Starter {

    // 使用自己产品的相关参数替换下列参数。
    private static final String productId = "x";
    private static final String apiKey = "x";

    public static void main(String args[]) {
        String url = String.format("https://tts.dui.ai/runtime/v2/synthesize?productId=%s&apikey=%s", productId, apiKey);

        String requestParams = String.format("{\n" +
                 "    \"context\": {\n" +
                 "        \"productId\": \"%s\"\n" +
                 "    },\n" +
                 "    \"request\": {\n" +
                 "        \"requestId\": \"%s\",\n" +
                 "        \"audio\": {\n" +
                 "            \"audioType\": \"mp3\",\n" +
                 "            \"sampleBytes\": 2,\n" +
                 "            \"sampleRate\": 16000,\n" +
                 "            \"channel\": 1\n" +
                 "        },\n" +
                 "        \"tts\": {\n" +
                 "            \"text\": \"%s\",\n" +
                 "            \"textType\": \"text\",\n" +
                 "            \"voiceId\": \"zhilingf\"\n" +
                 "        }\n" +
                 "    }\n" +
                 "}", productId, UUID.randomUUID(), "你好");

        TTSHttpClient asrliteHttpClient = new TTSHttpClient();
        asrliteHttpClient.doTTSHttpClient(url, requestParams);
    }
}
