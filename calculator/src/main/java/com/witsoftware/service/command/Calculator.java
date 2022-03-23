package com.witsoftware.service.command;

public class Calculator {
    private Double total = 0d;

    public void operation(String operator, Double operand) {

        switch (operator) {
            case "+":
                total += operand;
                break;
            case "-":
                total -= operand;
                break;
            case "*":
                total *= operand;
                break;
            case "/":
                total /= operand;
                break;
            default:
                throw new IllegalArgumentException(String.format("parameter %s is invalid", operator));
        }

    }

    public Double getTotal() {
        return this.total;
    }

}
