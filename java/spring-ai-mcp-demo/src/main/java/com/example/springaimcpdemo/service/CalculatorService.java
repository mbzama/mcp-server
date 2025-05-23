package com.example.springaimcpdemo.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * Calculator service providing basic mathematical operations as tools for the LLM
 */
@Service
public class CalculatorService {

    @Tool(description = "Add two numbers")
    public double add(double a, double b) {
        return a + b;
    }

    @Tool(description = "Subtract second number from first number")
    public double subtract(double a, double b) {
        return a - b;
    }

    @Tool(description = "Multiply two numbers")
    public double multiply(double a, double b) {
        return a * b;
    }

    @Tool(description = "Divide first number by second number")
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

    @Tool(description = "Calculate the power of a number")
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    @Tool(description = "Calculate the square root of a number")
    public double squareRoot(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        return Math.sqrt(number);
    }
}