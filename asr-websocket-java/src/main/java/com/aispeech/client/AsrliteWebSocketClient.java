package com.aispeech.client;

import org.java_websocket.client.WebSocketClient;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;

public class AsrliteWebSocketClient extends BaseClient {
    public Session session;


    protected void start(String authType, String authParams) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        String uri = appendAuthParams(authType, authParams);
        try {
            session = container.connectToServer(WebSocket.class, URI.create(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param requestData 请求参数
     * @param filePath    语音识别文件
     * @param authType    auth类型，这里为deviceName
     * @param authParams  auth参数，合成请求uri，示例："{\"deviceName\":\"xxx\",\"productId\":\"xxx\",\"deviceSecret\":\"xxx\",\"server\":\"xxx\","serverType":"asr","apiKey":""}"
     * @param step        分帧发送音频步长，推荐ogg的音频用400，wav/pcm的音频用3200
     */
    public void doAsrWebSocketClient(String requestData, String filePath, String authType, String authParams, Integer step) {
        AsrliteWebSocketClient asrliteWebSocketClientApp = new AsrliteWebSocketClient();
        asrliteWebSocketClientApp.start(authType, authParams);
        try {
            InputStream inputStream = new FileInputStream(filePath);
            int read = 0;
            byte[] bytes = new byte[step];
            ByteBuffer byteBuffer = null;
            asrliteWebSocketClientApp.session.getBasicRemote().sendText(requestData);
            while ((read = inputStream.read(bytes)) != -1) {
                asrliteWebSocketClientApp.session.getBasicRemote().sendBinary(byteBuffer.wrap(bytes));
            }
            byte[] closebytes = new byte[0];
            asrliteWebSocketClientApp.session.getBasicRemote().sendBinary(byteBuffer.wrap(closebytes));
            while (true) {
                Thread.sleep(65000);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
