package org.example.component;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session管理管理工具类
 */
@Slf4j
public class WsSessionManager {

    /**
     * 保存连接 session 的地方
     */
    public static ConcurrentHashMap<String, Session> SESSION_POOL = new ConcurrentHashMap<>();

    /**
     * 添加 session
     *
     * @param key
     */
    public static void add(String key, Session session) {
        // 添加 session
        SESSION_POOL.put(key, session);
    }

    /**
     * 删除 session,会返回删除的 session
     *
     * @param key
     * @return
     */
    public static Session remove(String key) {
        // 删除 session
        return SESSION_POOL.remove(key);
    }

    /**
     * 删除并同步关闭连接
     *
     * @param key
     */
    public static void removeAndClose(String key) {
        Session session = remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                log.error("删除并同步关闭连接异常:{}", e.getMessage(), e);
            }
        }
    }

    /**
     * 获得 session
     *
     * @param key
     * @return
     */
    public static Session get(String key) {
        // 获得 session
        return SESSION_POOL.get(key);
    }
}
