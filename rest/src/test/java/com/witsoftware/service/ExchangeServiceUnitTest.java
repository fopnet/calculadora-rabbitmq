package com.witsoftware.service;

import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeServiceUnitTest {

    @InjectMocks
    private ExchangeService exchangeService;

    @Mock
    private RabbitAdmin rabbitAdmin;

    @Before
    public void setup() {

    }

    @Test
    public void createExchange() {
        exchangeService.createExchange();

        BDDMockito.then(rabbitAdmin).should().declareExchange(any(Exchange.class));
    }

}
