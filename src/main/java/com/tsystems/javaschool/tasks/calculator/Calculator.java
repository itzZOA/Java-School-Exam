package com.tsystems.javaschool.tasks.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as
     *                  decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is
     *         invalid
     */

    private final DecimalFormat df = new DecimalFormat("#.###");

    public String evaluate(String expression) {
        if (expression == "" || expression == null) {

            return null;
        }

        df.setRoundingMode(RoundingMode.HALF_UP);
        LinkedList<String> postfix = infixToPostfix(expression);
        if (postfix == null) {
            return null;
        }
        Double result = evaluatePostfix(postfix);
        if (result == null) {
            return null;
        }
        return df.format(evaluatePostfix(postfix));
    }

    private LinkedList<String> infixToPostfix(String expression) {
        for (int i = 0; i < expression.length() - 1; i++) {
            char current = expression.charAt(i);
            char next = expression.charAt(i + 1);
            if (isOperator(current) && isOperator(next)) {
                return null;
            }
        }
        LinkedList<String> postfix = new LinkedList<>();
        LinkedList<Character> operators = new LinkedList<>();
        StringBuilder number = new StringBuilder();
        boolean pointExist = false;
        int openParenthesisCount = 0;
        int closeParenthesisCount = 0;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                if (c == '.') {
                    if (pointExist) {
                        return null;
                    }
                    pointExist = true;
                }
                number.append(c);
            } else {
                pointExist = false;
                if (number.length() > 0) {
                    postfix.add(number.toString());
                    number.setLength(0);
                }
                if (c == ' ') {
                    continue;
                }
                if(c == ',')
                    return null;
                if (c == '(') {
                    operators.push(c);
                    openParenthesisCount++;
                } else if (c == ')') {
                    closeParenthesisCount++;
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        postfix.add(String.valueOf(operators.pop()));
                    }
                    if (operators.isEmpty() || operators.peek() != '(') {
                        return null;
                    }
                    operators.pop();
                } else if (isOperator(c)) {
                    while (!operators.isEmpty() && operators.peek() != '('
                            && precedence(c) <= precedence(operators.peek())) {
                        postfix.add(String.valueOf(operators.pop()));
                    }
                    operators.push(c);
                }
            }
        }
        if (openParenthesisCount != closeParenthesisCount) {
            return null;
        }
        if (number.length() > 0) {
            postfix.add(number.toString());
        }
        while (!operators.isEmpty()) {
            postfix.add(String.valueOf(operators.pop()));
        }
        return postfix;
    }

    private Double evaluatePostfix(LinkedList<String> postfix) {
        LinkedList<BigDecimal> stack = new LinkedList<>();
        for (String token : postfix) {
            if (isOperator(token.charAt(0))) {
                BigDecimal b = stack.pop();
                BigDecimal a = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(a.add(b));
                        break;
                    case "-":
                        stack.push(a.subtract(b));
                        break;
                    case "*":
                        stack.push(a.multiply(b));
                        break;
                    case "/":
                        if (b.compareTo(BigDecimal.ZERO) == 0)
                            return null;
                        stack.push(a.divide(b, 4, RoundingMode.HALF_UP));
                        break;
                }
            } else {
                stack.push(new BigDecimal(token));
            }
        }

        return stack.pop().doubleValue();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char c) {
        if (c == '+' || c == '-') {
            return 1;
        }
        return 2;
    }
}
