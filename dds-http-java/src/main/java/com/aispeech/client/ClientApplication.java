package com.aispeech.client;

import com.alibaba.fastjson.JSONObject;

public class ClientApplication {

    private static void doDDSHttpRequest(String authType, JSONObject authParams) {
        DDSHttpClient ddsHttpClient = new DDSHttpClient(authType, authParams);
        ddsHttpClient.textDm();
        // ddsHttpClient.textNlu();
        // ddsHttpClient.systemSetting();
        // ddsHttpClient.skillSetting();
    }

    public static void main(String[] args) {
        JSONObject authParams = new JSONObject();
        authParams.put("alias", "prod");
        authParams.put("ddsServer", "dds.dui.ai");

        // 使用自己产品的相关参数替换下列参数。
        authParams.put("productId", "x");
        authParams.put("deviceName", "x");
        authParams.put("deviceSecret", "x");

        try {
            doDDSHttpRequest("deviceName", authParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

