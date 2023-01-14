package com.tsystems.javaschool.tasks.currencyconverter;
import java.text.DecimalFormat;

public class CurrencyConverter {

    /**
     * Currency converter which converts the input statement and returns the result value.
     *
     * @param dollarToEuroRate 1 Dollar equals XX Euro
     * @param statement input statement to convert
     * @return converted value
     */
    private static double dollarRate = 1.0;
    private static double euroRate = 1.0;


    public static void setEuroRate(double rate) {
        euroRate = rate;
    }

    public static String convertToDollar(String value) {
        if (value.endsWith("euro")) {
            double euroValue = Double.parseDouble(value.substring(0, value.length() - 4));
            return "$" + new DecimalFormat("#0.00").format(euroValue * dollarRate);
        }
        return value;
    }

    public static String convertToEuro(String value) {
        if (value.startsWith("$")) {
            double dollarValue = Double.parseDouble(value.substring(1));
            return new DecimalFormat("#0.00").format(dollarValue * euroRate) + "euro";
        }
        return value;
    }

    public String convert(double dollarToEuroRate, String statement) {
        setEuroRate(dollarToEuroRate);
        String[] parts = statement.split(" ");
        if (parts.length != 3) {
            return "Invalid expression";
        }
        String firstValue = parts[0];
        String operator = parts[1];
        String secondValue = parts[2];

        if (!firstValue.endsWith("euro") && !firstValue.startsWith("$")) {
            return "Invalid currency";
        }
        if (!secondValue.endsWith("euro") && !secondValue.startsWith("$")) {
            return "Invalid currency";
        }
        if (firstValue.endsWith("euro") && secondValue.startsWith("$")) {
            return "Cannot perform operation between different currencies";
        }
        if (firstValue.startsWith("$") && secondValue.endsWith("euro")) {
            return "Cannot perform operation between different currencies";
        }

        double firstNum = Double.parseDouble(firstValue.replace("$", "").replace("euro", ""));
        double secondNum = Double.parseDouble(secondValue.replace("$", "").replace("euro", ""));

        if (operator.equals("+")) {
            if (firstValue.endsWith("euro")) {
                return convertToDollar(Double.toString(firstNum + secondNum) + "euro");
            } else {
                return "$" + Double.toString(firstNum + secondNum);
            }
        } else if (operator.equals("-")) {
            if (firstValue.endsWith("euro")) {
                return convertToDollar(Double.toString(firstNum - secondNum) + "euro");
            } else {
                return "$" + Double.toString(firstNum - secondNum);
            }
        } else {
            return "Invalid operator";
        }
    }
}
