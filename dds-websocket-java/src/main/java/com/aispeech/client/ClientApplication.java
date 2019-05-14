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

        // 使用自己产品的相关参数替换下列参数。
        authParams.put("productId", "000000001");
        authParams.put("deviceName", "ae0169e4764b11e9a9700b31182119fe");
        authParams.put("deviceSecret", "b83fa39e764b11e9bc0c9fb0a0ae2c88");
        authParams.put("ddsServer", "dds.dui.ai");

        try {
            doDDSWebSocketRequest("deviceName", authParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

