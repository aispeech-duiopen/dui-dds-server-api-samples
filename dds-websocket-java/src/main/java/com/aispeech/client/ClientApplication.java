package com.aispeech.client;

import com.alibaba.fastjson.JSONObject;

public class ClientApplication {

    private static void doDDSWebSocketRequest(String authType, JSONObject authParams) {
        DDSWebSocketClient ddsWSClient = new DDSWebSocketClient(authType, authParams);
        ddsWSClient.textRequest();
        // ddsWSClient.triggerIntent();
        // ddsWSClient.audioRequest("audio/8k.wav", 3200);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ddsWSClient.close();
    }

    /**
     * alias :       分支号
     * productId:    产品ID
     * deviceName:   设备ID
     * deviceSecret: 设备注册后，auth服务下发的设备密钥
     */
    public static void main(String[] args) {
        JSONObject authParams = new JSONObject();
        authParams.put("alias", "prod");
        authParams.put("productId", "278578029");
        authParams.put("deviceName", "33f35b18c34f8c35c2521e408b3733b8");
        authParams.put("deviceSecret", "14511da111e246909a59f4fc6020cfdd");
        authParams.put("ddsServer", "dds.dui.ai");

        try {
            doDDSWebSocketRequest("deviceName", authParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

