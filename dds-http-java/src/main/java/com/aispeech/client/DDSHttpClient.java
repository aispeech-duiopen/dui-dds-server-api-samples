package com.aispeech.client;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.util.UUID;

public class DDSHttpClient extends BaseClient {

    private String ddsUrl;
    private HttpClient ddsClient;

    DDSHttpClient(String authType, JSONObject authParams) {
        ddsUrl = buildUrl("https", authType, authParams);
        ddsClient = HttpClientBuilder.create()
                                     .build();
    }

    // 发送文本请求对话。
    public void textDm() {
        JSONObject textParams = JSONObject.parseObject(String.format("{\n" +
                "    \"aiType\": \"dm\",\n" +
                "    \"topic\": \"nlu.input.text\",\n" +
                "    \"recordId\": \"%s\",\n" +
                "    \"refText\": \"苏州的天气\"\n" +
                "}", UUID.randomUUID() + ""));

        HttpPost post = new HttpPost(ddsUrl);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json; charset=utf-8");
        try {
            StringEntity body = new StringEntity(JSONObject.toJSONString(textParams), "utf-8");
            post.setEntity(body);
            HttpResponse resp = ddsClient.execute(post);
            print(EntityUtils.toString(resp.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 发送触发意图请求。
    public void triggerIntent() {
        JSONObject textParams = JSONObject.parseObject(String.format("{\n" +
                "    \"aiType\": \"dm\",\n" +
                "    \"topic\": \"dm.input.intent\",\n" +
                "    \"recordId\": \"%s\",\n" +
                "    \"skillId\": \"2018040200000004\",\n" +
                "    \"task\": \"查询天气\",\n" +
                "    \"intent\": \"天气\",\n" +
                "    \"slots\": {\n" +
                "        \"国内城市\": \"苏州\"\n" +
                "    }\n" +
                "}", UUID.randomUUID() + ""));

        HttpPost post = new HttpPost(ddsUrl);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json; charset=utf-8");
        try {
            StringEntity body = new StringEntity(JSONObject.toJSONString(textParams), "utf-8");
            post.setEntity(body);
            HttpResponse resp = ddsClient.execute(post);
            print(EntityUtils.toString(resp.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 做系统级配置。
    public void systemSetting() {
        JSONObject textParams = JSONObject.parseObject("{\n" +
                "    \"topic\": \"system.settings\",\n" +
                "    \"settings\": [\n" +
                "        {\n" +
                "            \"key\": \"location\",\n" +
                "            \"value\": {\n" +
                "                \"longitude\": 80,\n" +
                "                \"latitude\": 120,\n" +
                "                \"address\": \"china\",\n" +
                "                \"city\": \"suzhou\",\n" +
                "                \"time\": \"2019-04-01T09:00:00+0800\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}");

        HttpPost post = new HttpPost(ddsUrl);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json; charset=utf-8");
        try {
            StringEntity body = new StringEntity(JSONObject.toJSONString(textParams), "utf-8");
            post.setEntity(body);
            HttpResponse resp = ddsClient.execute(post);
            print(EntityUtils.toString(resp.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void skillSetting() {
        JSONObject textParams = JSONObject.parseObject("{\n" +
                "    \"topic\": \"skill.settings\",\n" +
                "    \"skillId\": \"2018040200000004\",\n" +
                "    \"settings\": [\n" +
                "        {\n" +
                "            \"key\": \"city\",\n" +
                "            \"value\": \"苏州\"\n" +
                "        }\n" +
                "    ]\n" +
                "}");

        HttpPost post = new HttpPost(ddsUrl);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json; charset=utf-8");
        try {
            StringEntity body = new StringEntity(JSONObject.toJSONString(textParams), "utf-8");
            post.setEntity(body);
            HttpResponse resp = ddsClient.execute(post);
            print(EntityUtils.toString(resp.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
