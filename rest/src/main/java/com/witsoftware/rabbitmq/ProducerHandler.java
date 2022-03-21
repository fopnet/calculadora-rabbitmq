package com.witsoftware.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.witsoftware.model.Equation;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerHandler implements ErrorHandler, MessageListener {

    // @RabbitListener(queues = "my-queue")
    public void handleMessage(Message message) {
        this.onMessage(message);
    }

    public void handleError(Throwable th) {
        log.error("error received on producer : " + th.toString());
    }

    @Override
    public void onMessage(Message message) {
        try {
            Equation equation = new ObjectMapper().readValue(message.getBody(), Equation.class);
            // Equation equation = (Equation) SerializationUtils.deserialize(messageBody.getBody());
            log.info("equation received on producer !");
            log.info(equation.toString());
        } catch (RuntimeException re) {
            log.error(re.getMessage(), re);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}
