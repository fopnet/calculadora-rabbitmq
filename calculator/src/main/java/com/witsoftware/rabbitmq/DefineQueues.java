package com.witsoftware.rabbitmq;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefineQueues {

    @Value("${witsoftware.rabbitmq.queue}")
    String queueName;

    @Value("${witsoftware.rabbitmq.exchange}")
    String exchange;

    @Value("${witsoftware.rabbitmq.routingkey}")
    private String routingkey;

    private final RabbitAdmin rabbitAdmin;

    @PostConstruct
    public void createQueue() {
        rabbitAdmin.declareQueue(new Queue(queueName, false));
        rabbitAdmin.declareBinding(
                new Binding(queueName, Binding.DestinationType.QUEUE, exchange, routingkey, new HashMap<>()));
    }

}
