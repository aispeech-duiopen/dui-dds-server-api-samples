package com.aispeech.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;

public class AsrliteHttpClient extends BaseClient {
    /**
     * @param requestData 请求参数
     * @param fileName 语音识别文件
     * @param url 请求地址
     */
    public void doAsrHttpClient(String url, String requestData, String fileName) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("file", new File(fileName), ContentType.create("application/octet-stream"), fileName)
                .addTextBody("params", requestData)
                .build();
        httpPost.setEntity(entity);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            //File file = new File("asr/AsrHttpResult.txt");
            //FileOutputStream outputStream = new FileOutputStream(file);
            //response.getEntity().writeTo(outputStream);
            print(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
