package com.aispeech.client;

import com.alibaba.fastjson.JSONObject;

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
        JSONObject textParams = JSONObject.parseObject(String.format("{\n" +
                "    \"aiType\": \"dm\",\n" +
                "    \"topic\": \"nlu.input.text\",\n" +
                "    \"recordId\": \"%s\",\n" +
                "    \"refText\": \"苏州的天气\"\n" +
                "}", UUID.randomUUID() + ""));
        try {
            client.send(JSONObject.toJSONString(textParams));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void triggerIntent() {
        JSONObject intentParams = JSONObject.parseObject(String.format("{\n" +
                "    \"aiType\": \"dm\",\n" +
                "    \"topic\": \"dm.input.intent\",\n" +
                "    \"recordId\": \"%s\",\n" +
                "    \"skillId\": \"2018040200000004\",\n" +
                "    \"intent\": \"查询天气\",\n" +
                "    \"task\": \"天气\",\n" +
                "    \"slots\": {\n" +
                "        \"国内城市\": \"苏州\"\n" +
                "    }\n" +
                "}", UUID.randomUUID() + ""));
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

        JSONObject asrParams = JSONObject.parseObject(String.format("{\n" +
                "    \"topic\": \"recorder.stream.start\",\n" +
                "    \"recordId\": \"%s\",\n" +
                "    \"audio\": {\n" +
                "        \"audioType\": \"wav\",\n" +
                "        \"sampleRate\": 8000,\n" +
                "        \"channel\": 1,\n" +
                "        \"sampleBytes\": 2\n" +
                "    }\n" +
                "}", UUID.randomUUID() + ""));

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

    public void systemSettings() {
        JSONObject settingParams = JSONObject.parseObject("{\n" +
                "        \"topic\": \"system.settings\",\n" +
                "        \"settings\": [\n" +
                "            {\n" +
                "                \"key\": \"location\",\n" +
                "                \"value\": {\n" +
                "                    \"longitude\": \"80\",\n" +
                "                    \"latitude\": \"120\",\n" +
                "                    \"address\": \"china\",\n" +
                "                    \"city\": \"suzhou\",\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }");
        try {
            client.send(JSONObject.toJSONString(settingParams));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void skillSettings() {
        JSONObject settingParams = JSONObject.parseObject("{\n" +
                "        \"topic\": \"skill.settings\",\n" +
                "        \"skillId\": \"2018040200000004\",\n" +
                "        \"option\": \"set\",\n" +
                "        \"settings\": [\n" +
                "            {\n" +
                "                \"key\": \"city\",\n" +
                "                \"value\": \"苏州\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }");
        try {
            client.send(JSONObject.toJSONString(settingParams));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        client.close();
    }
}
