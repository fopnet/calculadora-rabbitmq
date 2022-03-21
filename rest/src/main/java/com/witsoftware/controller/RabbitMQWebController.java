package com.witsoftware.controller;

import java.util.Optional;
import java.util.UUID;

import com.witsoftware.model.Equation;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/calculator/")
@Slf4j
public class RabbitMQWebController {

	@Autowired
	private final RabbitTemplate rabbitTemplate;

	@Value("${witsoftware.rabbitmq.queue}")
	String queueName;

	@Value("${witsoftware.rabbitmq.exchange}")
	String exchange;

	@Value("${witsoftware.rabbitmq.routingkey}")
	private String routingkey;

	public RabbitMQWebController(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@GetMapping(value = "/sum")
	public String producer(@RequestParam("a") String a, @RequestParam("b") String b) {

		String uniqueId = UUID.randomUUID().toString();
		log.info("Unique identifier:" + uniqueId);
		Equation equation = new Equation();
		equation.setFirstOperand(Double.parseDouble(a));
		equation.setSecondOperand(Double.parseDouble(b));
		equation.setOperador("+");

		// equation.setFirstOperand(a);
		// equation.setSecondOperand(b);
		rabbitTemplate.setEncoding("utf-8");
		rabbitTemplate.setExchange(exchange);
		rabbitTemplate.setRoutingKey(routingkey);
		rabbitTemplate.setDefaultReceiveQueue(queueName);
		rabbitTemplate.setReplyTimeout(2000);
		rabbitTemplate.setReceiveTimeout(2000);

		byte[] body = SerializationUtils.serialize(equation);
		MessageProperties props = new MessageProperties();
		props.getHeaders().put("uniqueId", uniqueId);
		props.setContentType("application/json");
		props.setContentEncoding("utf-8");

		Message message = new Message(body, props);
		Object result = rabbitTemplate.convertSendAndReceive(exchange, routingkey, message);
		// Object result = rabbitTemplate.convertSendAndReceive(equation);
		// String foo = (String) rabbitTemplate.receiveAndConvert();

		return "The result is " + Optional.ofNullable(result).orElse("result could not be null\n").toString();
	}

}
