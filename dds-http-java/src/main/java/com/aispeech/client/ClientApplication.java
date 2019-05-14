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

        // 使用自己产品的相关参数替换下列参数。
        authParams.put("productId", "000000001");
        authParams.put("deviceName", "ae0169e4764b11e9a9700b31182119fe");
        authParams.put("deviceSecret", "b83fa39e764b11e9bc0c9fb0a0ae2c88");
        authParams.put("ddsServer", "dds.dui.ai");

        try {
            doDDSHttpRequest("deviceName", authParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

