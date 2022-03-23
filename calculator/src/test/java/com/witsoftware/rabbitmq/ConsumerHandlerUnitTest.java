package com.witsoftware.rabbitmq;

import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;

import com.witsoftware.model.Equation;
import com.witsoftware.service.CalculatorService;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
// @ExtendWith(MockitoExtension.class)
// @TestPropertySource(properties = { "witsoftware.rabbitmq.correlationKey=correlationId" })
public class ConsumerHandlerUnitTest {

    @InjectMocks
    private ConsumerHandler consumerHandler;

    @Mock
    private CalculatorService calculator;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private Logger log;

    private Equation equation;
    private Message message;
    private MessageProperties props;

    @Before
    public void setup() {
        equation = new Equation();
        equation.setFirstOperand(1d);
        equation.setSecondOperand(2d);
        equation.setOperador("operator");

        ReflectionTestUtils.setField(consumerHandler, "correlationKey", "correlationKey");

        props = new MessageProperties();
        props.setReplyTo("fulano");
        props.getHeaders().put("correlationKey", "correlationId");

        message = new Message("body".getBytes(), props);
    }

    @Test
    public void deserialize() {

        try (MockedStatic<SerializationUtils> serialization = Mockito.mockStatic(SerializationUtils.class)) {
            serialization.when(() -> SerializationUtils.deserialize(message.getBody())).thenReturn(equation);

            consumerHandler.onMessage(message);

            assertThat(SerializationUtils.deserialize(message.getBody()), Is.is(equation));
        }

        InOrder inOrder = inOrder(calculator, rabbitTemplate);
        inOrder.verify(calculator).calculate(equation);
        // inOrder.verify(log)
        //         .info(String.format("Consumer handler received correlation id %s successfully", "correlationId"));
        inOrder.verify(rabbitTemplate).send(eq(props.getReplyTo()), any(Message.class));

        inOrder.verifyNoMoreInteractions();

    }

}
