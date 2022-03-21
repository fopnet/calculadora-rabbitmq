package com.witsoftware.config;

import com.witsoftware.rabbitmq.ProducerHandler;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig /*implements RabbitListenerConfigurer*/ {

	@Value("${witsoftware.rabbitmq.queue}")
	String queueName;

	@Value("${witsoftware.rabbitmq.exchange}")
	String exchange;

	@Value("${witsoftware.rabbitmq.routingkey}")
	private String routingkey;


	@Bean
	Queue queue() {
		// return new Queue(queueName, false, false, false, null);
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange directExchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	TopicExchange topicExchange() {
		return new TopicExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchangeBean) {
		return BindingBuilder.bind(queue).to(exchangeBean).with(routingkey);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, ProducerHandler handler,
			MessageListenerAdapter adapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		// container.setMessageListener(adapter);
		// container.setMessageListener(handler);
		container.setErrorHandler(handler);
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(ProducerHandler handler) {
		return new MessageListenerAdapter(handler, "handleMessage");
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		rabbitTemplate.setEncoding("utf-8");
		rabbitTemplate.setDefaultReceiveQueue(queueName);
		rabbitTemplate.setExchange(exchange);
		rabbitTemplate.setRoutingKey(routingkey);
		return rabbitTemplate;
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
		// return new Jackson2JsonMessageConverter() {
		// @Override
		// public Object fromMessage(Message message, Object conversionHint) throws MessageConversionException {
		// 	message.getMessageProperties().setContentType("application/json");
		// 	return super.fromMessage(message, conversionHint);
		// }
		// };
	}
	/*
		@Bean
		public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
			return new MappingJackson2MessageConverter();
		}
	
		@Bean
		public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
			DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
			factory.setMessageConverter(mappingJackson2MessageConverter());
			return factory;
		}
	
		@Override
		public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
			registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
		}
		*/
}
