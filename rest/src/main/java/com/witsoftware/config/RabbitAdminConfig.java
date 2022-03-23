package com.witsoftware.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RabbitAdminConfig {
    private final CachingConnectionFactory connectionFactory;

    @Bean
    public RabbitAdmin admin() {
        return new RabbitAdmin(connectionFactory);
    }
}
