package com.aispeech.client;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.UUID;

public class DDSWebSocketClient extends BaseClient {

    private CustomWebsocketClient client;

    /**
     * @param authType auth类型，这里为deviceName
     * @param authParams auth参数，合成请求uri，示例："{\"deviceName\":\"xxx\",\"productId\":\"xxx\",\"deviceSecret\":\"xxx\",\"server\":\"xxx\","serverType":"websocket","apiKey":""}"
     */
    DDSWebSocketClient(String authType, JSONObject authParams) {
        String uri = buildUrl("ws", authType, authParams);
        try {
            client = new CustomWebsocketClient(new URI(uri));
            try {
                client.connectBlocking();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void textRequest() {
        JSONObject textParams = new JSONObject();
        textParams.put("aiType", "dm");
        textParams.put("topic", "nlu.input.text");
        textParams.put("recordId", UUID.randomUUID() + "");
        textParams.put("refText", "苏州的天气");
        try {
            client.send(JSONObject.toJSONString(textParams));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void triggerIntent() {
        JSONObject intentParams = new JSONObject();
        intentParams.put("aiType", "dm");
        intentParams.put("topic", "dm.input.intent");
        intentParams.put("recordId", UUID.randomUUID() + "");
        intentParams.put("skillId", "2018040200000004");
        intentParams.put("intent", "查询天气");
        intentParams.put("task", "天气");

        JSONObject slots = new JSONObject();
        slots.put("城市", "苏州");
        intentParams.put("slots", slots);
        try {
            client.send(JSONObject.toJSONString(intentParams));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param audioPath 语音识别文件
     * @param step 分帧发送音频步长，推荐ogg的音频用400，wav/pcm的音频用3200
     */
    public void audioRequest(String audioPath, Integer step) {

        JSONObject asrParams = new JSONObject();
        asrParams.put("topic", "recorder.stream.start");
        asrParams.put("recordId", UUID.randomUUID() + "");

        JSONObject audio = new JSONObject();
        audio.put("audioType", "wav");
        audio.put("sampleRate", 8000);
        audio.put("channel", 1);
        audio.put("sampleBytes", 2);
        asrParams.put("audio", audio);

        InputStream inputStream = null;
        try {
            client.send(JSONObject.toJSONString(asrParams));

            inputStream = new FileInputStream(audioPath);

            byte[] bytes = new byte[step];
            ByteBuffer byteBuffer = null;
            while (inputStream.read(bytes) != -1) {
                client.send(byteBuffer.wrap((bytes)));
            }
            byte[] closeBytes = new byte[0];
            client.send(byteBuffer.wrap((closeBytes)));
            // session.getBasicRemote()
            //        .sendBinary(byteBuffer.wrap(closeBytes));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        client.close();
    }
}
