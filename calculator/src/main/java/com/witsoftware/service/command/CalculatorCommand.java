package com.witsoftware.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CalculatorCommand implements Command {
    private String operator;
    private Double operand;
    private Calculator calculator;

    public void execute() {
        calculator.operation(this.operator, operand);
    }

}
