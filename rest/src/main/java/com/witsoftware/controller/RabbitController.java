package com.witsoftware.controller;

import static java.util.Optional.of;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.witsoftware.builder.AdditionMessageBuilder;
import com.witsoftware.builder.DivisionMessageBuilder;
import com.witsoftware.builder.MultiplicationMessageBuilder;
import com.witsoftware.builder.SubtractionMessageBuilder;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/calculator/")
@RequiredArgsConstructor
public class RabbitController {

	private final RabbitTemplate rabbitTemplate;

	@Value("${witsoftware.rabbitmq.correlationKey}")
	private String correlationKey; // already setted in rabbitTemplate

	@GetMapping(value = "/sum")
	public ResponseEntity<Map<String, String>> sum(@RequestParam("a") String first, @RequestParam("b") String second) {

		Message message = AdditionMessageBuilder.builder()
				.correlationKey(correlationKey)
				.firstOperand(first)
				.secondOperand(second)
				.build()
				.buildAmqpMessage();

		Object result = rabbitTemplate.convertSendAndReceive(message);

		return ok(of(result));
	}

	@GetMapping(value = "/minus")
	public ResponseEntity<Map<String, String>> minus(@RequestParam("a") String first,
			@RequestParam("b") String second) {

		Message message = SubtractionMessageBuilder.builder()
				.correlationKey(correlationKey)
				.firstOperand(first)
				.secondOperand(second)
				.build()
				.buildAmqpMessage();

		Object result = rabbitTemplate.convertSendAndReceive(message);

		return ok(of(result));
	}

	@GetMapping(value = "/multiply")
	public ResponseEntity<Map<String, String>> multiply(@RequestParam("a") String first,
			@RequestParam("b") String second) {

		Message message = MultiplicationMessageBuilder.builder()
				.correlationKey(correlationKey)
				.firstOperand(first)
				.secondOperand(second)
				.build()
				.buildAmqpMessage();

		Object result = rabbitTemplate.convertSendAndReceive(message);

		return ok(of(result));
	}

	@GetMapping(value = "/divide")
	public ResponseEntity<Map<String, String>> divide(@RequestParam("a") String first,
			@RequestParam("b") String second) {

		Message message = DivisionMessageBuilder.builder()
				.correlationKey(correlationKey)
				.firstOperand(first)
				.secondOperand(second)
				.build()
				.buildAmqpMessage();

		Object result = rabbitTemplate.convertSendAndReceive(message);

		return ok(of(result));
	}

	private static ResponseEntity<Map<String, String>> ok(Optional<Object> optResult) {
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("result", optResult.orElse("").toString());
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

}
