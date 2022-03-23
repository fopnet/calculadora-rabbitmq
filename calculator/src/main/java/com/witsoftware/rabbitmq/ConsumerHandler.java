package com.witsoftware.rabbitmq;

import com.witsoftware.model.Equation;
import com.witsoftware.service.CalculatorService;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerHandler implements ErrorHandler, MessageListener {

    private final CalculatorService calculator;
    private final RabbitTemplate rabbitTemplate;

    @Value("${witsoftware.rabbitmq.correlationKey}")
    private String correlationKey; // already setted in rabbitTemplate

    public void handleError(Throwable th) {
        log.error("Received: " + th.toString());
    }

    @Override
    @RabbitListener(queues = { "my-queue" })
    public void onMessage(Message message) {
        try {
            final Equation equation = (Equation) SerializationUtils.deserialize(message.getBody());
            log.info("message received on consumer !");
            log.info(equation.toString());

            MessageProperties props = message.getMessageProperties();

            String correlationId = props.getHeaders().get(correlationKey).toString();

            rabbitTemplate.setCorrelationKey(correlationId);

            Double result = calculator.calculate(equation);
            Message replyMessage = new Message(String.valueOf(result).getBytes(), props);
            log.info(String.format("Consumer handler received correlation id %s successfully", correlationId));

            rabbitTemplate.send(props.getReplyTo(), replyMessage);

        } catch (RuntimeException re) {
            log.error(re.getMessage(), re);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}
