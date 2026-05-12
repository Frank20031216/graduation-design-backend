package com.ruoyi.mdm.websocket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.ruoyi.mdm.event.ProductionOrderChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.mdm.domain.dto.ProductionOrderQueryDTO;
import com.ruoyi.mdm.domain.entity.ProductionOrder;
import com.ruoyi.mdm.service.IProductionOrderService;

@Component
public class ProductionOrderWebSocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(ProductionOrderWebSocketHandler.class);

    // 存储活跃会话：key=sessionId，value=会话对象
    private final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    // 存储客户端订阅条件：key=sessionId，value=查询条件
    private final ConcurrentHashMap<String, ProductionOrderQueryDTO> sessionSubscriptions = new ConcurrentHashMap<>();

    private final IProductionOrderService productionOrderService;

    @Autowired
    public ProductionOrderWebSocketHandler(IProductionOrderService productionOrderService) {
        this.productionOrderService = productionOrderService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("新的WebSocket连接建立: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String payload = message.getPayload();
            log.info("收到WebSocket消息: {}", payload);

            JSONObject jsonObject = JSON.parseObject(payload);
            ProductionOrderQueryDTO queryDTO = new ProductionOrderQueryDTO();

            // 解析查询参数
            if (jsonObject.containsKey("customerName")) {
                queryDTO.setCustomerName(jsonObject.getString("customerName"));
            }
            if (jsonObject.containsKey("productCategory")) {
                queryDTO.setProductCategory(jsonObject.getString("productCategory"));
            }
            if (jsonObject.containsKey("specificationModel")) {
                queryDTO.setSpecificationModel(jsonObject.getString("specificationModel"));
            }
            if (jsonObject.containsKey("isProduced")) {
                queryDTO.setIsProduced(jsonObject.getString("isProduced"));
            }
            queryDTO.setPageNum(jsonObject.getIntValue("pageNum", 1));
            queryDTO.setPageSize(jsonObject.getIntValue("pageSize", 10));

            // 关键：记录当前客户端的订阅条件
            sessionSubscriptions.put(session.getId(), queryDTO);

            // 查询并返回数据
            List<ProductionOrder> productionOrderList = productionOrderService.selectProductionOrderList(
                    queryDTO, queryDTO.getPageNum(), queryDTO.getPageSize());

            JSONObject response = new JSONObject();
            response.put("code", 200);
            response.put("type", "QUERY_RESULT"); // 标记消息类型：查询结果
            response.put("message", "查询成功");
            response.put("data", productionOrderList);
            session.sendMessage(new TextMessage(response.toJSONString()));

        } catch (Exception e) {
            log.error("处理WebSocket消息失败", e);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("code", 500);
            errorResponse.put("message", "查询失败: " + e.getMessage());
            session.sendMessage(new TextMessage(errorResponse.toJSONString()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        sessionSubscriptions.remove(session.getId()); // 清理订阅条件
        log.info("WebSocket连接关闭: {}", session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket传输错误", exception);
    }

    /**
     * 数据变更时，主动推送给匹配的客户端
     * @param changedOrder 变更的生产订单（新增/修改/删除）
     */
    public void pushOrderUpdate(ProductionOrder changedOrder) {
        // 遍历所有订阅的客户端
        for (Map.Entry<String, ProductionOrderQueryDTO> entry : sessionSubscriptions.entrySet()) {
            String sessionId = entry.getKey();
            ProductionOrderQueryDTO subscribeCondition = entry.getValue();
            WebSocketSession targetSession = findSessionById(sessionId);

            // 1. 校验会话是否有效
            if (targetSession == null || !targetSession.isOpen()) {
                sessionSubscriptions.remove(sessionId);
                continue;
            }

            // 2. 判断该客户端是否订阅了此订单的相关条件（精准推送）
            boolean isMatch = true;
            // 客户名称匹配
            if (subscribeCondition.getCustomerName() != null
                    && !subscribeCondition.getCustomerName().equals(changedOrder.getCustomerName())) {
                isMatch = false;
            }
            // 产品类别匹配
            if (subscribeCondition.getProductCategory() != null
                    && !subscribeCondition.getProductCategory().equals(changedOrder.getProductCategory())) {
                isMatch = false;
            }
            // 规格型号匹配（可根据需求扩展）
            if (subscribeCondition.getSpecificationModel() != null
                    && !subscribeCondition.getSpecificationModel().equals(changedOrder.getSpecificationModel())) {
                isMatch = false;
            }

            // 3. 匹配则推送最新数据
            if (isMatch) {
                try {
                    // 查询该客户端订阅条件下的最新数据
                    List<ProductionOrder> latestData = productionOrderService.selectProductionOrderList(
                            subscribeCondition, subscribeCondition.getPageNum(), subscribeCondition.getPageSize());

                    // 构建推送消息
                    JSONObject pushMsg = new JSONObject();
                    pushMsg.put("code", 200);
                    pushMsg.put("type", "DATA_UPDATE"); // 标记消息类型：数据更新
                    pushMsg.put("message", "数据已更新");
                    pushMsg.put("updateTime", System.currentTimeMillis());
                    pushMsg.put("data", latestData);

                    // 发送推送消息
                    targetSession.sendMessage(new TextMessage(pushMsg.toJSONString()));
                    log.info("向客户端{}推送生产订单更新消息", sessionId);
                } catch (IOException e) {
                    log.error("推送消息给客户端{}失败", sessionId, e);
                }
            }
        }
    }

    /**
     * 根据sessionId查找会话对象
     */
    private WebSocketSession findSessionById(String sessionId) {
        for (WebSocketSession session : sessions) {
            if (session.getId().equals(sessionId)) {
                return session;
            }
        }
        return null;
    }

    /**
     * 广播消息给所有客户端（全量推送，备用）
     */
    public void broadcast(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    log.error("广播消息失败", e);
                }
            }
        }
    }

    @EventListener // Spring事件监听注解
    public void onProductionOrderChange(ProductionOrderChangeEvent event) {
        ProductionOrder changedOrder = event.getProductionOrder();
        log.info("收到生产订单{}事件，开始推送更新", event.getChangeType());
        // 调用原有推送逻辑
        pushOrderUpdate(changedOrder);
    }
}