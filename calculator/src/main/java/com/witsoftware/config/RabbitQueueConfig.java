package com.witsoftware.config;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @Configuration
@RequiredArgsConstructor
@Slf4j
public class RabbitQueueConfig {
    private final RabbitAdmin rabbitAdmin;

    @Value("${witsoftware.rabbitmq.exchange}")
    String exchangeName;

    @Value("${witsoftware.rabbitmq.routingkey}")
    private String routingkey;

    @Value("${witsoftware.rabbitmq.queue}")
    String queueName;

    @PostConstruct
    public void binding() {
        rabbitAdmin.declareQueue(new Queue(queueName, false));
        Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchangeName, routingkey,
                new HashMap<>());
        rabbitAdmin.declareBinding(binding);

        log.info("Binding queue %s with exchange %s routed by %s", queueName, exchangeName, routingkey);
    }

}
