package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tio.core.Tio;
import org.tio.utils.hutool.StrUtil;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.starter.TioWebSocketServerBootstrap;

@RestController
@RequestMapping("/push")
public class PushController {

    @Autowired
    private TioWebSocketServerBootstrap bootstrap;

    /**
     * 消息群发
     */
    @GetMapping("/msg")
    public void pushMessage() {

        String sendToUser = "sendToUser hello tio websocket spring boot starter";
        String sendToGroup = "sendToGroup hello tio websocket spring boot starter";

        Tio.sendToUser(bootstrap.getServerTioConfig(), "2", WsResponse.fromText(sendToUser, "utf-8"));
        //Tio.sendToAll(bootstrap.getServerTioConfig(), WsResponse.fromText(msg,"utf-8"));
        Tio.sendToGroup(bootstrap.getServerTioConfig(), "m1", WsResponse.fromText(sendToGroup, "utf-8"));
    }
}
