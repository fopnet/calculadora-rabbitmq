package com.witsoftware.config;

import com.witsoftware.rabbitmq.ConsumerHandler;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RabbitAdminConfig {

    @Value("${witsoftware.rabbitmq.exchange}")
    String exchangeName;

    // @Value("${witsoftware.rabbitmq.routingkey}")
    // private String routingkey;

    @Value("${witsoftware.rabbitmq.queue}")
    String queueName;

    private final CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitAdmin admin() {
        connectionFactory.setHost("localhost");
        // connectionFactory.setPort();
        connectionFactory.setPublisherReturns(true);
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public SimpleMessageListenerContainer container(RabbitAdmin admin, ConsumerHandler handler) {

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setErrorHandler(handler);

        container.setMessageListener(handler);
        // container.setMessageListener(admin.getRabbitTemplate());

        container.afterPropertiesSet();
        container.start();

        log.info("SimpleMessageListenerContainer created success");

        return container;
    }
}
