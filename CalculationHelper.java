package hn.bw.de.eu.eqwl;

import android.util.Log;

/**
 * Created by Oliver on 14.02.2016.
 */
public class CalculationHelper {

    private static String TAG = "CalculationHelper";
    private RandomNumberHelper rNH = new RandomNumberHelper();

    public Task getEqualCalculation() {

        int difficulty = 1;
        double maxNumber = 15;
        double minNumber = 2;
        int[] operators;
        int result = -1;

        difficulty = getDifficulty();
        operators = getOperators(difficulty);
        maxNumber = getMaxNumber(difficulty);
        minNumber = getMinNumber(difficulty);

        result = rNH.getRandomNumberInt((int) minNumber, (int) maxNumber);

        Calculation calc1 = getCalc(result, minNumber, maxNumber, operators, difficulty, false);
        Calculation calc2 = getCalc(result, minNumber, maxNumber, operators, difficulty, true);

        return new Task(calc1, calc2, true);
    }

    public Task getUnEqualCalculation() {

        int difficulty = 1;
        double maxNumber = 15;
        double minNumber = 2;
        int[] operators;
        int result = -1;

        difficulty = getDifficulty();
        operators = getOperators(difficulty);
        maxNumber = getMaxNumber(difficulty);
        minNumber = getMinNumber(difficulty);

        result = rNH.getRandomNumberInt((int) minNumber, (int) maxNumber);

        Calculation calc1 = getCalc(result, minNumber, maxNumber, operators, difficulty, false);
        if (rNH.getRandomNumberInt(0, 100) < 50) {
            result += 3;
        } else {
            result -= 3;
        }
        Calculation calc2 = getCalc(result, minNumber, maxNumber, operators, difficulty, true);

        return new Task(calc1, calc2, false);
    }

    private double getMinNumber(int difficulty) {
        double minNumber = -1;
        switch (difficulty) {
            case 1:
                minNumber = 2;
                break;
            case 2:
                minNumber = 5;
                break;
            case 3:
                minNumber = 10;
                break;
            case 4:
                minNumber = 20;
                break;
        }
        return minNumber;
    }

    public Calculation getCalc(int result, double minNumber, double maxNumber, int[] operators, int difficulty, boolean modifyOperators) {
        float currentResult = -1;
        float randomNumber1 = -1;
        float randomNumber2 = -1;
        String operatorString = "";

        if (modifyOperators) {
            int i = 0;
            for (int o : operators) {
                operators[i] = rNH.getRandomNumberInt(0, 1);
                i++;
            }
        }
        int counter = 0;
        while (currentResult != result) {
            randomNumber1 = rNH.getRandomNumberInt((int) minNumber, (int) maxNumber);
            randomNumber2 = rNH.getRandomNumberInt((int) (minNumber - minNumber + 1), (int) (maxNumber + minNumber));
            Log.d(TAG, "Result: " + result + " | MinNumber: " + minNumber + " | MaxNumber: " + maxNumber +
                    " | Operator: " + operators[0] + " | RandNumber1: " + randomNumber1 + " | RandomNumber2: " + randomNumber2);
            switch (difficulty) {
                case 1:
                    currentResult = calcWithOperator(randomNumber1, randomNumber2, operators[0]);
                    operatorString = getOperatorString(operators[0]);
                    break;
                case 2:
                    currentResult = calcWithOperator(randomNumber1, randomNumber2, operators[0]);
                    operatorString = getOperatorString(operators[0]);
                    break;
                case 3:
                    currentResult = calcWithOperator(randomNumber1, randomNumber2, operators[0]);
                    operatorString = getOperatorString(operators[0]);
                    break;
                case 4:
                    currentResult = calcWithOperator(randomNumber1, randomNumber2, operators[0]);
                    operatorString = getOperatorString(operators[0]);
                    break;
            }
            counter++;
            if (counter > 30) {
                operators[0] = rNH.getRandomNumberInt(0, 4);
                counter = 0;
            }
        }
        String calcString = (int)randomNumber1 + " " + operatorString + " " + (int)randomNumber2;
        return new Calculation(calcString, (int)result);
    }

    public String getOperatorString(int o) {
        String operatorString = "";
        switch (o) {
            case (0):
                operatorString = "+";
                break;
            case 1:
                operatorString = "-";
                break;
            case 2:
                operatorString = "x";
                break;
            case 3:
                operatorString = ":";
                break;
        }
        return operatorString;
    }

    public float calcWithOperator(float number1, float number2, int operator) {
        float result = -1;
        switch (operator) {
            case 0:
                result = number1 + number2;
                break;
            case 1:
                result = number1 - number2;
                break;
            case 2:
                result = number1 * number2;
                break;
            case 3:
                result = number1 / number2;
                break;
        }
        return result;
    }

    public double getMaxNumber(int difficulty) {
        double maxNumber = -1;
        switch (difficulty) {
            case 1:
                maxNumber = 15;
                break;
            case 2:
                maxNumber = 50;
                break;
            case 3:
                maxNumber = 100;
                break;
            case 4:
                maxNumber = 200;
                break;
        }
        return maxNumber;
    }

    public int[] getOperators(int difficulty) {
        int[] operators = new int[4];

        switch (difficulty) {
            case 1:
                operators[0] = rNH.getRandomNumberInt(0, 4);
                break;
            case 2:
                for (int i = 0; i < 2; i++) {
                    operators[i] = rNH.getRandomNumberInt(0, 4);
                }
                break;
            case 3:
                for (int i = 0; i < 3; i++) {
                    operators[i] = rNH.getRandomNumberInt(0, 4);
                }
                break;
            case 4:
                for (int i = 0; i < 4; i++) {
                    operators[i] = rNH.getRandomNumberInt(0, 4);
                }
                break;

        }
        return operators;
    }

    public int getDifficulty() {
        int difficulty = 1;
        if (Variables.SCORE < 15) {
            difficulty = 1;
        } else if (Variables.SCORE < 30) {
            for (int i = 0; i < 2; i++) {
                difficulty = 2;
            }
        } else if (Variables.SCORE < 45) {
            for (int i = 0; i < 3; i++) {
                difficulty = 3;
            }
        } else {
            for (int i = 0; i < 4; i++) {
                difficulty = 4;
            }
        }
        return difficulty;
    }

}
