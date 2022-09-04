package org.example.config;


import lombok.extern.slf4j.Slf4j;
import org.example.component.WsSessionManager;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;


@Component
@ServerEndpoint("/websocket/{id}")
@Slf4j
public class WebSocketServer {


    /**
     *  连接建立成功后调用
     */
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session) {
        log.info("客户端" + id + "连接建立.");
        WsSessionManager.add(id, session);

        try {
            sendMessage(id, "客户端" + id + "连接建立.");
        } catch (IOException e) {
            log.error("WebSocket IO异常");
        }
    }

    /**
     * 连接关闭时调用
     */
    @OnClose
    public void onClose(@PathParam(value = "id") String id, Session session) {
        log.info("有一连接关闭：{}", id);
        WsSessionManager.remove(id);
    }

    /**
     * 收到客户端消息后调用
     */
    @OnMessage
    public void onMessage(@PathParam(value = "id") String id, String message) {
        log.info("来自客户端的消息:" + message);
        String[] messages = message.split("[|]");
        try {
            if (messages.length > 1) {
                sendToUser(messages[0], messages[1], id);
            } else {
                sendToAll(messages[0]);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 发生错误时回调
     */
    @OnError
    public void onError(Session session, Throwable e) {
        log.error("WebSocket发生错误:{}", e.getMessage(), e);
    }

    /**
     * 发送消息
     * @param message 要发送的消息
     */
    private void sendMessage(String id, String message) throws IOException {
        Session session = WsSessionManager.get(id);
        session.getBasicRemote().sendText(message);
    }

    private void sendToUser(String message, String sendClientId, String myId) throws IOException {
        if (sendClientId == null || WsSessionManager.get(sendClientId) == null) {
            sendMessage(myId, "当前客户端不在线");
        } else {
            sendMessage(sendClientId, message);
        }

    }

    private void sendToAll(String message) throws IOException {
        for (String key : WsSessionManager.SESSION_POOL.keySet()) {
            WsSessionManager.get(key).getBasicRemote().sendText(message);
        }
    }
}
