package com.witsoftware.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import com.witsoftware.model.Equation;
import com.witsoftware.service.command.Calculator;
import com.witsoftware.service.command.CalculatorCommand;
import com.witsoftware.service.command.CalculatorCommand.CalculatorCommandBuilder;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class CalculatorServiceUnitTest {
    @InjectMocks
    private CalculatorService calculatorService;
    @Captor
    ArgumentCaptor<Calculator> calculatorCaptor;

    private Equation equation;

    @Before
    public void setup() {
        equation = new Equation();
        equation.setFirstOperand(1d);
        equation.setSecondOperand(2d);
        equation.setOperador("operador");
    }

    @Test
    @Ignore(value = "Incomplete")
    public void calculate() {

        CalculatorCommandBuilder commandbuilder1 = mock(CalculatorCommandBuilder.class);
        CalculatorCommand calculator1 = mock(CalculatorCommand.class);

        given(commandbuilder1.build()).willReturn(calculator1);

        try (MockedStatic<CalculatorCommand> command1 = Mockito.mockStatic(CalculatorCommand.class)) {
            command1.when(() -> CalculatorCommand.builder()).thenReturn(commandbuilder1);

            // try (MockedStatic<CalculatorCommand> command2 = Mockito.mockStatic(CalculatorCommand.class)) {
            // command1.when(() -> CalculatorCommand.builder()).thenReturn(commandbuilder2);

            calculatorService.calculate(equation);

            then(commandbuilder1).should().calculator(calculatorCaptor.capture());
            then(commandbuilder1).should().operator(equation.getOperatorOne());
            then(commandbuilder1).should().operand(equation.getFirstOperand());
            then(commandbuilder1).should().build();
            then(calculator1).should().execute();

            // then(commandbuilder2).should().calculator(calculatorCaptor.capture());
            // then(commandbuilder2).should().operator(equation.getOperatorOne());
            // then(commandbuilder2).should().operand(equation.getFirstOperand());
            // then(commandbuilder2).should().build();
            // then(calculator1).should().execute();

            then(calculatorCaptor.getValue()).should().getTotal();

        }
    }

}
