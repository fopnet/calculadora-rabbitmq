package com.witsoftware.builder;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import com.witsoftware.model.Equation;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.utils.SerializationUtils;

@RunWith(MockitoJUnitRunner.class)
public class AdditionMessageBuilderUnitTest {

    // @InjectMocks
    private AdditionMessageBuilder messageBuilderMock;

    private AdditionMessageBuilder messageBuilder;

    @Before
    public void setup() {
        messageBuilder = AdditionMessageBuilder.builder()
                .correlationKey("correlationKey")
                .firstOperand("first")
                .secondOperand("second")
                .build();
    }

    @Test
    public void getOperator() {
        assertEquals("+", messageBuilder.getOperator());
    }

    @Ignore
    public void buildAmqpMessage() {
        byte[] body = "body".getBytes();
        Message messageBuilt = new Message(body, new MessageProperties());

        given(messageBuilderMock.buildAmqpMessage()).willReturn(messageBuilt);

        try (MockedStatic<SerializationUtils> serialization = Mockito.mockStatic(SerializationUtils.class)) {
            String correlationId = "correlationId";

            serialization.when(() -> SerializationUtils.serialize(any(Equation.class))).thenReturn(body);

            try (MockedStatic<UUID> uid = Mockito.mockStatic(UUID.class)) {
                uid.when(() -> UUID.randomUUID()).thenReturn(correlationId);

                assertThat(messageBuilderMock.buildAmqpMessage(), Is.is(messageBuilt));

                //  log.info("Unique identifier:" + correlationId);
            }

        }
    }
}
