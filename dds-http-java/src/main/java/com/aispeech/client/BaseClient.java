package com.aispeech.client;

import com.alibaba.fastjson.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriComponents;

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


    public String buildUrl(String protocol, String authType,
                           JSONObject authJson) {
        JSONObject auth = authJson;
        UriComponentsBuilder uriComponents = UriComponentsBuilder.newInstance()
                                                                 .scheme(protocol)
                                                                 .host(auth.getString("ddsServer"))
                                                                 .path("/dds/v2/{alias}")
                                                                 .queryParam("productId", auth.getString("productId"));

        if (authType.equals("deviceName")) {
            String timestamp = new Date().getTime() + "";
            String nonce = UUID.randomUUID() + "";
            String sig = getSignature(auth.getString("deviceName") + nonce + auth.getString("productId") + timestamp, auth.getString("deviceSecret"));

            uriComponents.queryParam("deviceName", auth.getString("deviceName"))
                         .queryParam("nonce", nonce)
                         .queryParam("timestamp", timestamp)
                         .queryParam("sig", sig);

        } else if (authType.equals("apiKey")) {
            uriComponents.queryParam("apiKey", auth.getString("apiKey"));
        }
        UriComponents url = uriComponents.queryParam("serviceType", "websocket")
                                         .build()
                                         .expand(auth.getString("alias"))
                                         .encode();

        return url.toString();
    }
}
