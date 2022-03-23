package com.witsoftware.model;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EquationUnitTest {

    private Equation equation;

    @Before
    public void setup() {
        equation = new Equation();
        equation.setFirstOperand(1d);
        equation.setSecondOperand(2d);
    }

    @Test
    public void getOperatorOne_Positive() {
        equation.setFirstOperand(+1d);
        assertThat(equation.getOperatorOne(), Is.is("+"));
    }

    @Test
    public void getOperatorOne_Negative() {
        equation.setFirstOperand(-1d);
        assertThat(equation.getOperatorOne(), Is.is("-"));
    }

    @Test
    public void getOperatorTwo_Positive() {
        equation.setOperador("+");
        assertThat(equation.getOperatorTwo(), Is.is("+"));
    }

    @Test
    public void getOperatorTwo_Negative() {
        equation.setOperador("-");
        assertThat(equation.getOperatorTwo(), Is.is("-"));
    }
}
