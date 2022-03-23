package com.witsoftware.service;

import com.witsoftware.model.Equation;
import com.witsoftware.service.command.Calculator;
import com.witsoftware.service.command.CalculatorCommand;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public Double calculate(Equation equation) {

        Calculator calculator = new Calculator();

        // Create command operation and execute it
        CalculatorCommand.builder()
                .calculator(calculator)
                .operator(equation.getOperatorOne())
                .operand(equation.getFirstOperand())
                .build()
                .execute();

        CalculatorCommand.builder()
                .calculator(calculator)
                .operator(equation.getOperatorTwo())
                .operand(equation.getSecondOperand())
                .build()
                .execute();

        return calculator.getTotal();
    }
}