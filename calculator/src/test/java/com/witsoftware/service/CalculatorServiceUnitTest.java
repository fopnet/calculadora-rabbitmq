package com.witsoftware.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.witsoftware.model.Equation;
import com.witsoftware.service.command.Calculator;
import com.witsoftware.service.command.CalculatorCommand;
import com.witsoftware.service.command.CalculatorCommand.CalculatorCommandBuilder;

import org.junit.Before;
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
    public void calculate() {

        CalculatorCommandBuilder commandbuilder = mock(CalculatorCommandBuilder.class);
        CalculatorCommand calculatorCmd = mock(CalculatorCommand.class);

        given(commandbuilder.calculator(any(Calculator.class))).willReturn(commandbuilder);
        given(commandbuilder.operator(equation.getOperatorOne())).willReturn(commandbuilder);
        given(commandbuilder.operand(equation.getFirstOperand())).willReturn(commandbuilder);

        given(commandbuilder.operator(equation.getOperatorTwo())).willReturn(commandbuilder);
        given(commandbuilder.operand(equation.getSecondOperand())).willReturn(commandbuilder);
        given(commandbuilder.build()).willReturn(calculatorCmd);

        try (MockedStatic<CalculatorCommand> command1 = Mockito.mockStatic(CalculatorCommand.class)) {
            command1.when(() -> CalculatorCommand.builder()).thenReturn(commandbuilder);

            calculatorService.calculate(equation);

            then(commandbuilder).should(times(2)).calculator(calculatorCaptor.capture());
            then(commandbuilder).should().operator(equation.getOperatorOne());
            then(commandbuilder).should().operand(equation.getFirstOperand());
            then(commandbuilder).should(times(2)).build();
            then(calculatorCmd).should(times(2)).execute();

            assertNotNull(calculatorCaptor.getValue());

        }
    }

}
