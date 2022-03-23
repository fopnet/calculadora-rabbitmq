package com.witsoftware.config;

import com.witsoftware.rabbitmq.ConsumerHandler;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RabbitTemplateConfig {
    // private final ConnectionFactory connectionFactory;
    private final RabbitAdmin rabbitAdmin;

    @Value("${witsoftware.rabbitmq.queue}")
    String queueName;

    // @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, ConsumerHandler handler) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(handler);
        // container.setMessageListener(adapter);
        // container.setMessageListener(rabbitAdmin.getRabbitTemplate());
        container.setErrorHandler(handler);

        return container;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setDefaultReceiveQueue(queueName);
        rabbitTemplate.setUseDirectReplyToContainer(false);
        rabbitTemplate.setReplyAddress(queueName);
        rabbitTemplate.setReplyTimeout(20000);
        rabbitTemplate.setReceiveTimeout(20000);
        rabbitTemplate.setUserCorrelationId(true);
        return rabbitTemplate;
    }

}
