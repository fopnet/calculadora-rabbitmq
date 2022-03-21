package com.witsoftware.rabbitmq;

import com.witsoftware.model.Equation;
import com.witsoftware.service.CalculatorService;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
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

    // @RabbitListener(queues = "my-queue")
    public void handleMessage(Message message) {
        this.onMessage(message);
    }

    public void handleError(Throwable th) {
        log.error("Received: " + th.toString());
    }

    @Override
    public void onMessage(Message message) {
        try {
            final Equation equation = (Equation) SerializationUtils.deserialize(message.getBody());
            // final Equation equation = new ObjectMapper().readValue(message.getBody(), Equation.class);
            // Equation equation = (Equation) SerializationUtils.deserialize(messageBody.getBody());
            log.info("message received on consumer !");
            log.info(equation.toString());

            // MessageProperties props = message.getMessageProperties();

            // ReceiveAndReplyCallback<Equation, String> cb = eq -> String.valueOf(calculator.calculate(eq));

            // template.send(message.getMessageProperties().getReplyTo(), )
            // rabbitTemplate.setDefaultReceiveQueue(props.getConsumerQueue());

            if (rabbitTemplate.receiveAndReply((ReceiveAndReplyCallback<Equation, String>) eq -> {
                Double result = calculator.calculate(eq);
                return String.valueOf(result);
            })) {
                log.info("ConsumerHandler receive and reply success");
            }

            // if (template.receiveAndReply(message.getMessageProperties().getReplyTo(),
            //         (ReceiveAndReplyMessageCallback) message1 -> {
            //             message1.getMessageProperties().setHeader("foo", "bar");
            //             String body = String.valueOf(calculator.calculate(equation));
            //             return new Message(body.getBytes(), message1.getMessageProperties());
            //         })) {
            //     log.info("ConsumerHandler receive and reply success");
            // }

            // template.convertAndSend(pros.getReceivedExchange(), pros.getReceivedRoutingKey(),
            //         String.valueOf(resultMessage));

        } catch (RuntimeException re) {
            log.error(re.getMessage(), re);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}
