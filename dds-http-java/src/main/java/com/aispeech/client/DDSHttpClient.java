package com.aispeech.client;

import com.alibaba.fastjson.JSONArray;
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
        JSONObject textParams = new JSONObject();
        textParams.put("aiType", "dm");
        textParams.put("topic", "nlu.input.text");
        textParams.put("recordId", UUID.randomUUID() + "");
        textParams.put("refText", "苏州的天气");

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

    // 发送文本请求语义。
    public void textNlu() {
        JSONObject textParams = new JSONObject();
        textParams.put("aiType", "nlu");
        textParams.put("topic", "nlu.input.text");
        textParams.put("recordId", UUID.randomUUID() + "");
        textParams.put("refText", "苏州的天气");

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
        JSONObject textParams = new JSONObject();
        textParams.put("topic", "system.settings");

        JSONObject settingParams = new JSONObject();
        settingParams.put("key", "location");
        JSONObject valueParams = new JSONObject();
        valueParams.put("longitude", 80);
        valueParams.put("latitude", 120);
        valueParams.put("address", "china");
        valueParams.put("city", "suzhou");
        valueParams.put("time", "2019-04-01T09:00:00+0800");
        settingParams.put("value", valueParams);

        JSONArray settingArray = new JSONArray();
        settingArray.add(settingParams);
        textParams.put("settings", settingArray);

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
        JSONObject textParams = new JSONObject();
        textParams.put("topic", "skill.settings");
        textParams.put("skillId", "2018040200000004");

        JSONObject settingParams = new JSONObject();
        settingParams.put("key", "key1");
        settingParams.put("value", "value1");

        JSONArray settingArray = new JSONArray();
        settingArray.add(settingParams);
        textParams.put("settings", settingArray);

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
