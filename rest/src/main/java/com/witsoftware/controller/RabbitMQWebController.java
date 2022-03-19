package com.witsoftware.controller;

import com.witsoftware.model.Equacao;
import com.witsoftware.service.RabbitMQSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calculator/")
public class RabbitMQWebController {

	@Autowired
	RabbitMQSender rabbitMQSender;

	@GetMapping(value = "/sum")
	public String producer(@RequestParam("a") String a, @RequestParam("b") String b) {

		Equacao eq = new Equacao();
		eq.setA(Double.parseDouble(a));
		eq.setB(Double.parseDouble(b));
		eq.setOperador("+");
		rabbitMQSender.send(eq);

		return "Message sent to the RabbitMQ JavaInUse Successfully";
	}

}
