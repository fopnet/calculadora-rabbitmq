package com.witsoftware.service.command;

import static org.mockito.BDDMockito.then;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class CalculatorCommandUnitTest {
    @InjectMocks
    private CalculatorCommand command;
    @Mock
    private Calculator calculator;

    private String operator = "my-operator";
    private Double operand = 8.8d;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(command, "operator", operator);
        ReflectionTestUtils.setField(command, "operand", operand);
    }

    @Test
    public void execute() {

        command.execute();

        then(calculator).should().operation(operator, operand);
    }

}
