package com.witsoftware.service;

import com.witsoftware.model.Equacao;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("${witsoftware.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${witsoftware.rabbitmq.routingkey}")
	private String routingkey;	

	String kafkaTopic = "my_topic";
	
	public void send(Equacao company) {
		amqpTemplate.convertAndSend(exchange, routingkey, company);
		System.out.println("Send msg = " + company);
	    
	}
}