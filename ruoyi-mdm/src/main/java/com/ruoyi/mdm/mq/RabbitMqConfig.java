package com.ruoyi.mdm.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    // 交换机配置（3个核心交换机，对应异步通信场景）
    public static final String PROCESS_EVENT_EXCHANGE = "process.event.exchange"; // Flowable→APS
    public static final String BUSINESS_CALLBACK_EXCHANGE = "business.callback.exchange"; // APS→Flowable
    public static final String DEAD_LETTER_EXCHANGE = "dead.letter.exchange"; // 死信交换机

    // 队列配置（核心队列，接收异步消息）
    public static final String APS_CTP_CALCULATE_QUEUE = "aps.ctp.calculate.queue"; // APS接收订单计算请求消息
    public static final String APS_SCHEDULE_RETRY_QUEUE = "aps.schedule.retry.queue"; // APS接收重试消息队列
    public static final String APS_SUCCESS_CALLBACK_QUEUE = "aps.success.callback.queue"; // Flowable接收成功回调
    public static final String APS_EXCEPTION_CALLBACK_QUEUE = "aps.exception.callback.queue"; // Flowable接收异常回调
    public static final String DEAD_LETTER_QUEUE = "dead.letter.queue"; // 死信队列

    public static final String OMS_SUCCESS_CALLBACK_QUEUE = "oms.success.callback.queue"; // OMS接收成功回调消息
    public static final String OMS_ORDER_CREATE_QUEUE = "oms.order.create.queue"; // OMS接收订单创建消息
    public static final String OMS_DATA_PERSISTENCE_QUEUE = "oms.data.persistence.queue"; // OMS接收订单数据持久化消息

    public static final String OMS_WEBSOCKET_INFORM_QUEUE = "oms.websocket.inform.queue"; // OMS接收websocket通知消息队列
    // 路由键配置（异步消息路由，确保消息准确投递）
    public static final String APS_ORDER_ROUTING_KEY = "process.aps.order";
    public static final String APS_SUCCESS_CALLBACK_ROUTING_KEY = "callback.aps.success";
    public static final String APS_EXCEPTION_CALLBACK_ROUTING_KEY = "callback.aps.exception";
    public static final String DEAD_LETTER_ROUTING_KEY = "dead.letter.aps";
    public static final String APS_CTP_CALCULATE_ROUTING_KEY = "process.aps.ctp.calculate";
    public static final String APS_SCHEDULE_RETRY_ROUTING_KEY = "process.aps.schedule.retry";

    public static final String OMS_SUCCESS_CALLBACK_ROUTING_KEY = "callback.oms.success";
    public static final String OMS_ORDER_CREATE_ROUTING_KEY = "callback.oms.order.create";
    public static final String OMS_DATA_PERSISTENCE_ROUTING_KEY = "process.oms.data.persistence"; // OMS接收订单数据持久化消息路由键
    public static final String OMS_WEBSOCKET_INFORM_ROUTING_KEY = "process.oms.websocket.inform"; // OMS接收websocket通知消息路由键

    // 声明流程驱动交换机（Topic类型，支持路由匹配，异步发送消息依赖此交换机）
    @Bean
    public TopicExchange processEventExchange() {
        return ExchangeBuilder.topicExchange(PROCESS_EVENT_EXCHANGE).durable(true).build();
    }

    // 声明业务回调交换机
    @Bean
    public TopicExchange businessCallbackExchange() {
        return ExchangeBuilder.topicExchange(BUSINESS_CALLBACK_EXCHANGE).durable(true).build();
    }

    // 声明死信交换机（异常消息兜底）
    @Bean
    public TopicExchange deadLetterExchange() {
        return ExchangeBuilder.topicExchange(DEAD_LETTER_EXCHANGE).durable(true).build();
    }

    // 声明APS订单队列（绑定死信交换机，处理超时/异常消息）
    @Bean
    public Queue apsCtpCalculateRequestQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 30000); // 消息超时时间30秒
        arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        return QueueBuilder.durable(APS_CTP_CALCULATE_QUEUE).withArguments(arguments).build();
    }

    // 声明APS重排队列（绑定死信交换机，处理超时/异常消息）
    @Bean
    public Queue apsScheduleRetryQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 30000); // 消息超时时间30秒
        arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        return QueueBuilder.durable(APS_SCHEDULE_RETRY_QUEUE).withArguments(arguments).build();
    }

    // 声明成功回调队列
    @Bean
    public Queue apsSuccessCallbackQueue() {
        return QueueBuilder.durable(APS_SUCCESS_CALLBACK_QUEUE).build();
    }

    // 声明异常回调队列
    @Bean
    public Queue apsExceptionCallbackQueue() {
        return QueueBuilder.durable(APS_EXCEPTION_CALLBACK_QUEUE).build();
    }

    // 声明死信队列
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    // 声明OMS成功回调队列
    @Bean
    public Queue omsSuccessCallbackQueue() {
        return QueueBuilder.durable(OMS_SUCCESS_CALLBACK_QUEUE).build();
    }

    // 声明OMS订单创建队列
    @Bean
    public Queue omsOrderCreateQueue() {
        return QueueBuilder.durable(OMS_ORDER_CREATE_QUEUE).build();
    }

    // 声明APS数据持久化队列
    @Bean
    public Queue omsDataPersistenceQueue() {
        return QueueBuilder.durable(OMS_DATA_PERSISTENCE_QUEUE).build();
    }

    // 声明OMS websocket通知队列
    @Bean
    public Queue omsWebsocketInformQueue() {
        return QueueBuilder.durable(OMS_WEBSOCKET_INFORM_QUEUE).build();
    }

    // 队列与交换机绑定（确保异步消息能正确路由到对应队列）
    @Bean
    public Binding apsCtpCalculateRequestBinding() {
        return BindingBuilder.bind(apsCtpCalculateRequestQueue())
                .to(processEventExchange())
                .with(APS_CTP_CALCULATE_ROUTING_KEY);
    }

    // 声明APS重排队列与交换机绑定
    @Bean
    public Binding apsScheduleRetryBinding() {
        return BindingBuilder.bind(apsScheduleRetryQueue())
                .to(processEventExchange())
                .with(APS_SCHEDULE_RETRY_ROUTING_KEY);
    }

    @Bean
    public Binding apsSuccessCallbackBinding() {
        return BindingBuilder.bind(apsSuccessCallbackQueue())
                .to(businessCallbackExchange())
                .with(APS_SUCCESS_CALLBACK_ROUTING_KEY);
    }

    @Bean
    public Binding apsExceptionCallbackBinding() {
        return BindingBuilder.bind(apsExceptionCallbackQueue())
                .to(businessCallbackExchange())
                .with(APS_EXCEPTION_CALLBACK_ROUTING_KEY);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DEAD_LETTER_ROUTING_KEY);
    }

    @Bean
    public Binding omsSuccessCallbackBinding() {
        return BindingBuilder.bind(omsSuccessCallbackQueue())
                .to(businessCallbackExchange())
                .with(OMS_SUCCESS_CALLBACK_ROUTING_KEY);
    }

    @Bean
    public Binding omsOrderCreateBinding() {
        return BindingBuilder.bind(omsOrderCreateQueue())
                .to(processEventExchange())
                .with(OMS_ORDER_CREATE_ROUTING_KEY);
    }

    // 声明APS数据持久化队列与交换机绑定
    @Bean
    public Binding omsDataPersistenceBinding() {
        return BindingBuilder.bind(omsDataPersistenceQueue())
                .to(processEventExchange())
                .with(OMS_DATA_PERSISTENCE_ROUTING_KEY);
    }

    // 声明OMS websocket通知队列与交换机绑定
    @Bean
    public Binding omsWebsocketInformBinding() {
        return BindingBuilder.bind(omsWebsocketInformQueue())
                .to(processEventExchange())
                .with(OMS_WEBSOCKET_INFORM_ROUTING_KEY);
    }
}
