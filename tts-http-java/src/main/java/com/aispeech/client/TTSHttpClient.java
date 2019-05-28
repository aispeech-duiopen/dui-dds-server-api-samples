package com.aispeech.client;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TTSHttpClient extends BaseClient {
    /**
     * @param url 请求地址
     * @param requestData 请求参数
     */
    public void doTTSHttpClient(String url, String requestData) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);

        StringEntity body = new StringEntity(requestData, Charsets.UTF_8); // 编码需要设置为 UTF-8，否则中文合成会出错。
        body.setContentType("text/json");
        body.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
        httpPost.setEntity(body);

        FileOutputStream outputStream = null;
        try {
            HttpResponse response = httpClient.execute(httpPost);
            File file = new File("tts/test.mp3");
            outputStream = new FileOutputStream(file);
            response.getEntity().writeTo(outputStream);

            print(response.getStatusLine());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
