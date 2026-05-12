package com.ruoyi.mdm.mq;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ruoyi.mdm.domain.entity.ProductionOrder;
import com.ruoyi.mdm.service.IProductionOrderService;
import com.ruoyi.mdm.websocket.ProductionOrderWebSocketHandler;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;


@Component
public class RabbitMqConsumer {

    @Autowired
    private IProductionOrderService productionOrderService;

    @Autowired
    private ProductionOrderWebSocketHandler productionOrderWebSocketHandler;

    @RabbitListener(queues = RabbitMqConfig.OMS_DATA_PERSISTENCE_QUEUE)
    public void listenFlowableOrderPersistence(Message message, @Nullable @Header(AmqpHeaders.MESSAGE_ID) String messageId) {
        //从消息中获取订单信息
        String messageBody = new String(message.getBody());
        List<ProductionOrder> productionOrderList = JSON.parseObject(messageBody,
                new TypeReference<List<ProductionOrder>>() {});

        // 调用服务层方法批量创建生产订单
        productionOrderService.insertProductionOrders(productionOrderList);
    }


    @RabbitListener(queues = RabbitMqConfig.OMS_WEBSOCKET_INFORM_QUEUE)
    public void listenWebsocketInform(Message message, @Nullable @Header(AmqpHeaders.MESSAGE_ID) String messageId) {
        //从消息中获取订单信息
        String messageBody = new String(message.getBody());
        List<ProductionOrder> productionOrderList = JSON.parseObject(messageBody,
                new TypeReference<List<ProductionOrder>>() {});

        productionOrderWebSocketHandler.pushOrderUpdate(productionOrderList.get(0));

    }

}
