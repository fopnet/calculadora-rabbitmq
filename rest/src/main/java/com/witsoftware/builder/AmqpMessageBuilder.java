package com.witsoftware.builder;

import java.util.UUID;

import com.witsoftware.model.Equation;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.utils.SerializationUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@SuperBuilder
@NoArgsConstructor
abstract class AmqpMessageBuilder {
    private String firstOperand;
    private String secondOperand;
    private String correlationKey;

    abstract String getOperator();

    public Message buildAmqpMessage() {

        Equation equation = new Equation();
        equation.setFirstOperand(Double.parseDouble(firstOperand));
        equation.setSecondOperand(Double.parseDouble(secondOperand));
        equation.setOperador(getOperator());

        byte[] body = SerializationUtils.serialize(equation);
        String correlationId = UUID.randomUUID().toString();
        log.info("Unique identifier:" + correlationId);

        MessageProperties props = new MessageProperties();
        props.getHeaders().put(correlationKey, correlationId);
        props.setContentType("text/plain");

        return new Message(body, props);
    }

}
