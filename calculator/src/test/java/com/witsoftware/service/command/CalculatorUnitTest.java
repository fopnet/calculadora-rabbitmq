package com.witsoftware.service.command;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class CalculatorUnitTest {
    private Calculator calculator;

    @Before
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    public void sum() {
        calculator.operation("+", 5d);
        assertThat(calculator.getTotal(), Is.is(5d));
    }

    @Test
    public void minus() {
        calculator.operation("-", 5d);
        assertThat(calculator.getTotal(), Is.is(-5d));
    }

    @Test
    public void multiply() {
        calculator.operation("+", 5d);
        calculator.operation("*", 1d);
        assertThat(calculator.getTotal(), Is.is(5d));
    }

    @Test
    public void divide() {
        calculator.operation("+", 10d);
        calculator.operation("/", 2d);
        assertThat(calculator.getTotal(), Is.is(5d));
    }

    @Test
    public void dividendZero() {
        calculator.operation("/", 5d);
        assertThat(calculator.getTotal(), Is.is(0d));
    }

    @Test
    public void divideByZero() {
        calculator.operation("/", 0d);
        assertThat(calculator.getTotal(), Is.is(Double.NaN));
    }
}
