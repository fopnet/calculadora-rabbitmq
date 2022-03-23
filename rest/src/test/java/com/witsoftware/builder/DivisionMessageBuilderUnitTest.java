package com.witsoftware.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DivisionMessageBuilderUnitTest {

    private DivisionMessageBuilder messageBuilder;

    @Before
    public void setup() {
        messageBuilder = DivisionMessageBuilder.builder()
                .correlationKey("correlationKey")
                .firstOperand("first")
                .secondOperand("second")
                .build();
    }

    @Test
    public void getOperator() {
        assertEquals("/", messageBuilder.getOperator());
    }

}
