package com.aispeech.client;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

public class BaseClient {
    private static final String HMAC_SHA1 = "HmacSHA1";


    private static String byteToHexString(byte ib) {
        char[] Digit = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0f];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    private static String getSignature(String data, String key) {
        byte[] keyBytes = key.getBytes();
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] rawHmac = mac.doFinal(data.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : rawHmac) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }

    public static void print(Object obj) {
        System.out.println(obj.toString());
    }


    public String appendAuthParams(String authType, String authJson) {
        StringBuilder url = new StringBuilder();
        JSONObject auth = JSONObject.parseObject(authJson);
        if (authType.equals("deviceName")) {
            String timestamp = new Date().getTime() + "";
            String nonce = UUID.randomUUID() + "";
            String sig = getSignature(auth.getString("deviceName") + nonce + auth.getString("productId") + timestamp, auth.getString("deviceSecret"));
            if (auth.getString("serverType").equals("asr")) {
                url.append(auth.getString("server"))
                        .append("recoginize?");
            } else {
                url.append(auth.getString("server"))
                        .append("synthesize?");
            }
            url.append("productId=")
                    .append(auth.getString("productId"))
                    .append("&deviceName=")
                    .append(auth.getString("deviceName"))
                    .append("&timestamp=")
                    .append(timestamp)
                    .append("&nonce=")
                    .append(nonce)
                    .append("&sig=")
                    .append(sig);
            print("Url: " + url.toString());
        } else if (authType.equals("apikey")) {
            if (auth.getString("serverType").equals("asr")) {
                url.append(auth.getString("server"))
                        .append("recognize?");
            } else {
                url.append(auth.getString("server"))
                        .append("synthesize?");
            }
            url.append("productId=")
                    .append(auth.getString("productId"))
                    .append("&apikey=")
                    .append(auth.getString("apiKey"));
            print("Url: " + url.toString());
        }
        return url.toString();
    }
}
