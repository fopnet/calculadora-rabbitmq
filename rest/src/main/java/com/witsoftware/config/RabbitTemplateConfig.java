package com.witsoftware.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTemplateConfig {

	@Value("${witsoftware.rabbitmq.queue}")
	String queueName;

	@Value("${witsoftware.rabbitmq.exchange}")
	String exchangeName;

	@Value("${witsoftware.rabbitmq.routingkey}")
	private String routingkey;

	@Value("${witsoftware.rabbitmq.correlationKey}")
	private String correlationKey; // already setted in rabbitTemplate

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		// rabbitTemplate.setMessageConverter(jsonMessageConverter());
		rabbitTemplate.setRoutingKey(routingkey);
		rabbitTemplate.setExchange(exchangeName);
		rabbitTemplate.setDefaultReceiveQueue(queueName);
		rabbitTemplate.setUseDirectReplyToContainer(false);
		rabbitTemplate.setReplyTimeout(10000);
		rabbitTemplate.setReceiveTimeout(10000);

		rabbitTemplate.setEncoding("utf-8");
		rabbitTemplate.setCorrelationKey(correlationKey);

		return rabbitTemplate;
	}

}
