package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.websocket.starter.EnableTioWebSocketServer;

@SpringBootApplication
@EnableTioWebSocketServer
public class ClusterApp3 {


    public static void main(String[] args) {
        SpringApplication.run(ClusterApp3.class, args);
        System.out.println("启动成功....");
    }


}