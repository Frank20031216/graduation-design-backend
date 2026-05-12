package com.ruoyi.framework.config;

import com.ruoyi.mdm.websocket.ProductionOrderWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * WebSocket配置类
 *
 * @author Frank Feng
 * @date 2026-03-18
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ProductionOrderWebSocketHandler productionOrderWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册生产订单WebSocket处理器，设置路径为/ws/productionOrder
        registry.addHandler(productionOrderWebSocketHandler, "/ws/productionOrder")
                .setAllowedOrigins("*");
    }
}