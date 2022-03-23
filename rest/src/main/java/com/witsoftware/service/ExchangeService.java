package com.witsoftware.service;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeService {

    private final RabbitAdmin rabbitAdmin;

    @Value("${witsoftware.rabbitmq.exchange}")
    String exchangeName;

    @Bean
    @PostConstruct
    public void createExchange() {
        rabbitAdmin.declareExchange(ExchangeBuilder.directExchange(exchangeName).build());
        log.info(String.format("Exchange %s created", exchangeName));
    }
}
