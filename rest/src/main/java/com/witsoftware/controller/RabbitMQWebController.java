package com.witsoftware.controller;

import java.util.Optional;
import java.util.UUID;

import com.witsoftware.model.Equation;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/calculator/")
@Slf4j
@RequiredArgsConstructor
public class RabbitMQWebController {

	private final RabbitAdmin rabbitAdmin;

	@Value("${witsoftware.rabbitmq.queue}")
	String queueName;

	@Value("${witsoftware.rabbitmq.exchange}")
	String exchange;

	@Value("${witsoftware.rabbitmq.routingkey}")
	private String routingkey;


	@GetMapping(value = "/sum")
	public String producer(@RequestParam("a") String a, @RequestParam("b") String b) {

		Equation equation = new Equation();
		equation.setFirstOperand(Double.parseDouble(a));
		equation.setSecondOperand(Double.parseDouble(b));
		equation.setOperador("+");

		// RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
		RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitAdmin.getRabbitTemplate().getConnectionFactory());
		// rabbitTemplate.setSendConnectionFactorySelectorExpression(new LiteralExpression("literalValue"));
		rabbitTemplate.setRoutingKey(routingkey);
		rabbitTemplate.setDefaultReceiveQueue(queueName);
		rabbitTemplate.setUseDirectReplyToContainer(false);
		// rabbitTemplate.setReplyAddress(queueName);
		// rabbitTemplate.setUserCorrelationId(true);
		rabbitTemplate.setReplyTimeout(10000);

		byte[] body = SerializationUtils.serialize(equation);
		String correlationId = UUID.randomUUID().toString();
		log.info("Unique identifier:" + correlationId);

		MessageProperties props = new MessageProperties();
		props.getHeaders().put("correlationKey", correlationId);
		// props.setContentType("application/json");
		props.setContentType("text/plain");
		props.setCorrelationId("correlationKey");
		props.setContentEncoding("utf-8");
		// props.setReplyTo(routingkey);

		Message message = new Message(body, props);

		Object result = rabbitTemplate.convertSendAndReceive(exchange, routingkey, message);
		rabbitTemplate.setReceiveTimeout(10000);

		// Object result = rabbitTemplate.convertSendAndReceive(equation);
		// String foo = (String) rabbitTemplate.receiveAndConvert();

		return "The result is "
				+ Optional.ofNullable(result).orElse("result could not be null").toString().concat("\n");
	}

}
