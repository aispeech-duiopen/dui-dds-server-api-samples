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
        authParams.put("productId", "278578029");
        authParams.put("deviceName", "33f35b18c34f8c35c2521e408b3733b8");
        authParams.put("deviceSecret", "14511da111e246909a59f4fc6020cfdd");
        authParams.put("ddsServer", "dds.dui.ai");

        try {
            doDDSHttpRequest("deviceName", authParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

