package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

@Component
@Slf4j
public class MyWebSocketMsgHandler implements IWsMsgHandler {
    @Override
    public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        return httpResponse;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
        String userId = httpRequest.getParam("userId");
        String milkId = httpRequest.getParam("milkId");
        Tio.bindGroup(channelContext, milkId);
        Tio.bindUser(channelContext,userId);
        log.info("握手成功 userId:{}, groupId:{}", userId, milkId);
    }

    @Override
    public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        System.out.println("onBytes  接收到bytes消息");
        return null;
    }

    @Override
    public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
        System.out.println("onClose");
        return null;
    }

    @Override
    public Object onText(WsRequest wsRequest, String s, ChannelContext channelContext) throws Exception {
        System.out.println("onText 接收到文本消息：" + s);
        return "应答消息:" + s;
    }
}
