package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tio.websocket.starter.EnableTioWebSocketServer;

@SpringBootApplication
@EnableTioWebSocketServer
public class ClusterApp {
    public static void main(String[] args) {
        SpringApplication.run(ClusterApp.class, args);
        System.out.println("启动成功....");
    }
}