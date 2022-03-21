package com.witsoftware.service;

import com.witsoftware.model.Equation;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public Double calculate(Equation equation) {
        return equation.getFirstOperand() + equation.getSecondOperand();
    }
}