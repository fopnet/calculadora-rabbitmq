package com.witsoftware.controller;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.witsoftware.builder.AdditionMessageBuilder;
import com.witsoftware.builder.AdditionMessageBuilder.AdditionMessageBuilderBuilder;
import com.witsoftware.builder.DivisionMessageBuilder;
import com.witsoftware.builder.DivisionMessageBuilder.DivisionMessageBuilderBuilder;
import com.witsoftware.builder.MultiplicationMessageBuilder;
import com.witsoftware.builder.MultiplicationMessageBuilder.MultiplicationMessageBuilderBuilder;
import com.witsoftware.builder.SubtractionMessageBuilder;
import com.witsoftware.builder.SubtractionMessageBuilder.SubtractionMessageBuilderBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class RabbitControllerUnitTest {
    @InjectMocks
    private RabbitController rabbitController;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private String correlationKey = "my-correlationKey";
    private String first = "5";
    private String second = "10";

    @Before
    public void setup() {
        ReflectionTestUtils.setField(rabbitController, "correlationKey", correlationKey);
    }

    private static ResponseEntity<Map<String, String>> ok(Optional<Object> optResult) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", optResult.orElse("").toString());
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @Test
    public void sum() {

        AdditionMessageBuilderBuilder additiionBuilder = mock(AdditionMessageBuilderBuilder.class);
        given(additiionBuilder.correlationKey(correlationKey)).willReturn(additiionBuilder);
        given(additiionBuilder.firstOperand(first)).willReturn(additiionBuilder);
        given(additiionBuilder.secondOperand(second)).willReturn(additiionBuilder);

        AdditionMessageBuilder additionMessage = mock(AdditionMessageBuilder.class);
        given(additiionBuilder.build()).willReturn(additionMessage);

        Message message = mock(Message.class);
        given(additionMessage.buildAmqpMessage()).willReturn(message);

        given(rabbitTemplate.convertSendAndReceive(message)).willReturn(15d);

        try (MockedStatic<AdditionMessageBuilder> builder = Mockito.mockStatic(AdditionMessageBuilder.class)) {
            builder.when(() -> AdditionMessageBuilder.builder()).thenReturn(additiionBuilder);

            ResponseEntity<Map<String, String>> expected = rabbitController.sum(first, second);

            Object result = rabbitTemplate.convertSendAndReceive(message);

            ResponseEntity<Map<String, String>> actual = ok(of(result));

            assertEquals(expected, actual);
        }
    }

    @Test
    public void minus() {
        SubtractionMessageBuilderBuilder additiionBuilder = mock(SubtractionMessageBuilderBuilder.class);
        given(additiionBuilder.correlationKey(correlationKey)).willReturn(additiionBuilder);
        given(additiionBuilder.firstOperand(first)).willReturn(additiionBuilder);
        given(additiionBuilder.secondOperand(second)).willReturn(additiionBuilder);

        SubtractionMessageBuilder additionMessage = mock(SubtractionMessageBuilder.class);
        given(additiionBuilder.build()).willReturn(additionMessage);

        Message message = mock(Message.class);
        given(additionMessage.buildAmqpMessage()).willReturn(message);

        given(rabbitTemplate.convertSendAndReceive(message)).willReturn(15d);

        try (MockedStatic<SubtractionMessageBuilder> builder = Mockito.mockStatic(SubtractionMessageBuilder.class)) {
            builder.when(() -> SubtractionMessageBuilder.builder()).thenReturn(additiionBuilder);

            ResponseEntity<Map<String, String>> expected = rabbitController.minus(first, second);

            Object result = rabbitTemplate.convertSendAndReceive(message);

            ResponseEntity<Map<String, String>> actual = ok(of(result));

            assertEquals(expected, actual);
        }
    }

    @Test
    public void multiply() {

        MultiplicationMessageBuilderBuilder additiionBuilder = mock(MultiplicationMessageBuilderBuilder.class);
        given(additiionBuilder.correlationKey(correlationKey)).willReturn(additiionBuilder);
        given(additiionBuilder.firstOperand(first)).willReturn(additiionBuilder);
        given(additiionBuilder.secondOperand(second)).willReturn(additiionBuilder);

        MultiplicationMessageBuilder additionMessage = mock(MultiplicationMessageBuilder.class);
        given(additiionBuilder.build()).willReturn(additionMessage);

        Message message = mock(Message.class);
        given(additionMessage.buildAmqpMessage()).willReturn(message);

        given(rabbitTemplate.convertSendAndReceive(message)).willReturn(15d);

        try (MockedStatic<MultiplicationMessageBuilder> builder = Mockito
                .mockStatic(MultiplicationMessageBuilder.class)) {
            builder.when(() -> MultiplicationMessageBuilder.builder()).thenReturn(additiionBuilder);

            ResponseEntity<Map<String, String>> expected = rabbitController.multiply(first, second);

            Object result = rabbitTemplate.convertSendAndReceive(message);

            ResponseEntity<Map<String, String>> actual = ok(of(result));

            assertEquals(expected, actual);
        }
    }

    @Test
    public void divide() {

        DivisionMessageBuilderBuilder additiionBuilder = mock(DivisionMessageBuilderBuilder.class);
        given(additiionBuilder.correlationKey(correlationKey)).willReturn(additiionBuilder);
        given(additiionBuilder.firstOperand(first)).willReturn(additiionBuilder);
        given(additiionBuilder.secondOperand(second)).willReturn(additiionBuilder);

        DivisionMessageBuilder additionMessage = mock(DivisionMessageBuilder.class);
        given(additiionBuilder.build()).willReturn(additionMessage);

        Message message = mock(Message.class);
        given(additionMessage.buildAmqpMessage()).willReturn(message);

        given(rabbitTemplate.convertSendAndReceive(message)).willReturn(15d);

        try (MockedStatic<DivisionMessageBuilder> builder = Mockito
                .mockStatic(DivisionMessageBuilder.class)) {
            builder.when(() -> DivisionMessageBuilder.builder()).thenReturn(additiionBuilder);

            ResponseEntity<Map<String, String>> expected = rabbitController.divide(first, second);

            Object result = rabbitTemplate.convertSendAndReceive(message);

            ResponseEntity<Map<String, String>> actual = ok(of(result));

            assertEquals(expected, actual);
        }
    }

}
