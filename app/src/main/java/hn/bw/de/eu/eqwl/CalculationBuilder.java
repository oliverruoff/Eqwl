package hn.bw.de.eu.eqwl;

import android.util.Log;

/**
 * Created by Oliver on 17.02.2016.
 */
public class CalculationBuilder {

    private RandomNumberHelper rNH = new RandomNumberHelper();


    private static String TAG = "CalculationBuilder";

    public Task getRandomTask() {
        Calculation a;
        Calculation b = null;
        Task t;
        float result = rNH.getRandomNumberInt((int) (1 + (0.5 * Variables.SCORE)), 10 + Variables.SCORE);
        a = getCalcForResult(result);
        if (rNH.getRandomNumberInt(0, 100) > 50) {
            boolean foundOtherCalculation = false;
            while (!foundOtherCalculation) {
                b = getCalcForResult(result);
                if (!b.equals(a)) {
                    foundOtherCalculation = true;
                }
            }

            t = new Task(a, b, true);
        } else {
            float randomResultModifier = rNH.getRandomNumberInt(1, 7);
            if (rNH.getRandomNumberInt(0, 100) > 50) {
                result += randomResultModifier;
            } else {
                result -= randomResultModifier;
            }
            b = getCalcForResult(result);
            t = new Task(a, b, false);
        }
        return t;
    }

    private Calculation getCalcForResult(float result) {
        String calcString = "";
        float currentResult = -1;
        float randomNumber1 = 0;
        float randomNumber2 = 0;
        int minNumberReducer = 0;
        int calcTrys = 0;
        int allTrysForTask = 0;
        int minNumber = 0;
        int randomOperator = rNH.getRandomNumberInt(0, 4);
        while (currentResult != result) {
            if ((int) (1 + (0.5 * Variables.SCORE) - minNumberReducer) > 0) {
                minNumber = (int) (1 + (0.5 * Variables.SCORE) - minNumberReducer);
            } else {
                minNumber = 1;
            }
            randomNumber1 = rNH.getRandomNumberInt(minNumber, 10 + Variables.SCORE);
            randomNumber2 = rNH.getRandomNumberInt(minNumber, 10 + Variables.SCORE);
//            Log.d(TAG, "Result: " + result + " | RandomNumber1: " + randomNumber1 +
//                    " | RandomNumber2: " + randomNumber2 + " | RandomOperator: " +
//                    randomOperator + " | MinNumberReducer: " + minNumberReducer + " | Trys: " + calcTrys + " | All Trys: " + allTrysForTask);
            if (calcTrys > 40) {
                randomOperator = rNH.getRandomNumberInt(0, 4);
                calcTrys = 0;
            }
            switch (randomOperator) {
                case (0):
                    currentResult = randomNumber1 + randomNumber2;
                    break;
                case (1):
                    currentResult = randomNumber1 - randomNumber2;
                    break;
                case (2):
                    currentResult = randomNumber1 * randomNumber2;
                    break;
                case (3):
                    currentResult = randomNumber1 / randomNumber2;
                    break;
            }
            calcTrys++;
            minNumberReducer++;
            allTrysForTask++;
        }
        calcString = (int) randomNumber1 + " " + numberToOperator(randomOperator) + " " + (int) randomNumber2;
        Calculation calc = new Calculation(calcString, result, randomNumber1, randomNumber2, randomOperator);
        Log.d(TAG, "Found task @ " + allTrysForTask + " trys: " + calcString);
        return calc;
    }

    private String numberToOperator(int o) {
        String operator = "";
        switch (o) {
            case 0:
                operator = "+";
                break;
            case 1:
                operator = "-";
                break;
            case 2:
                operator = "x";
                break;
            case 3:
                operator = ":";
                break;
        }
        return operator;
    }
}
