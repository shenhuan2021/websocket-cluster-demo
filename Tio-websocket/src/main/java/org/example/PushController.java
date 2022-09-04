package org.example;

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
     * @param msg
     */
    @GetMapping("/msg")
    public void pushMessage(String msg){
        if (StrUtil.isEmpty(msg)){
            msg = "hello tio websocket spring boot starter";
        }
        Tio.sendToAll(bootstrap.getServerTioConfig(), WsResponse.fromText(msg,"utf-8"));
    }
}
