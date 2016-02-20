package hn.bw.de.eu.eqwl.Calculations;

/**
 * Created by Oliver on 14.02.2016.
 */
public class Calculation {

    public String calcString = "";
    public double result = -1;
    public int operator = -1;
    public float number1 = -1;
    public float number2 = -1;

    public Calculation(String calcString, double result, float number1, float number2, int operator) {
        this.calcString = calcString;
        this.result = result;
        this.number1 = number1;
        this.number2 = number2;
        this.operator = operator;
    }

    public boolean equals(Calculation c) {
        if (operator == c.operator) {
            if (number1 == c.number1 && number2 == c.number2) {
                return true;
            } else if (number1 == c.number2 && number2 == c.number1) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
