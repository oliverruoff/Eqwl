package hn.bw.de.eu.eqwl.Calculations;

import android.util.Log;

import hn.bw.de.eu.eqwl.Helper.RandomNumberHelper;
import hn.bw.de.eu.eqwl.Static.Variables;

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
            float randomResultModifier = rNH.getRandomNumberInt(1, 4);
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
        Calculation calc = null;
        float currentResult = -1;
        float randomNumber1 = 0;
        float randomNumber2 = 0;
        float randomNumber3 = 0;
        float minNumberReducer = 0;
        int calcTrys = 0;
        int allTrysForTask = 0;
        int minNumber = 0;
        int calcWithBrackets = 0;
        int randomOperator = rNH.getRandomNumberInt(0, 4);
        int randomOperator2 = 0;
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
            if (Variables.SCORE > 10) {
                calcWithBrackets = rNH.getRandomNumberInt(0, 100);
            }
            if (calcWithBrackets < 50) {
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
                calcString = (int) randomNumber1 + " " + numberToOperator(randomOperator) + " " + (int) randomNumber2;
                calc = new Calculation(calcString, result, randomNumber1, randomNumber2, -1, randomOperator, -1);
            } else {
                randomNumber3 = rNH.getRandomNumberInt(1, Variables.SCORE / 2);
                randomOperator2 = rNH.getRandomNumberInt(0, 4);
                switch (randomOperator) {
                    case (0):
                        switch (randomOperator2) {
                            case (0):
                                currentResult = (randomNumber1 + randomNumber2) + randomNumber3;
                                break;
                            case (1):
                                currentResult = (randomNumber1 + randomNumber2) - randomNumber3;
                                break;
                            case (2):
                                currentResult = (randomNumber1 + randomNumber2) * randomNumber3;
                                break;
                            case (3):
                                currentResult = (randomNumber1 + randomNumber2) / randomNumber3;
                                break;
                        }
                        break;
                    case (1):
                        switch (randomOperator2) {
                            case (0):
                                currentResult = (randomNumber1 - randomNumber2) + randomNumber3;
                                break;
                            case (1):
                                currentResult = (randomNumber1 - randomNumber2) - randomNumber3;
                                break;
                            case (2):
                                currentResult = (randomNumber1 - randomNumber2) * randomNumber3;
                                break;
                            case (3):
                                currentResult = (randomNumber1 - randomNumber2) / randomNumber3;
                                break;
                        }
                        break;
                    case (2):
                        switch (randomOperator2) {
                            case (0):
                                currentResult = (randomNumber1 * randomNumber2) + randomNumber3;
                                break;
                            case (1):
                                currentResult = (randomNumber1 * randomNumber2) - randomNumber3;
                                break;
                            case (2):
                                currentResult = (randomNumber1 * randomNumber2) * randomNumber3;
                                break;
                            case (3):
                                currentResult = (randomNumber1 * randomNumber2) / randomNumber3;
                                break;
                        }
                        break;
                    case (3):
                        switch (randomOperator2) {
                            case (0):
                                currentResult = (randomNumber1 / randomNumber2) + randomNumber3;
                                break;
                            case (1):
                                currentResult = (randomNumber1 / randomNumber2) - randomNumber3;
                                break;
                            case (2):
                                currentResult = (randomNumber1 / randomNumber2) * randomNumber3;
                                break;
                            case (3):
                                currentResult = (randomNumber1 / randomNumber2) / randomNumber3;
                                break;
                        }
                        break;
                }
                calcString = "(" + (int) randomNumber1 + " " + numberToOperator(randomOperator) + " " + (int) randomNumber2 + ") " + numberToOperator(randomOperator2) + " " + (int) randomNumber3;
                calc = new Calculation(calcString, result, randomNumber1, randomNumber2, randomNumber3, randomOperator, randomOperator2);
            }

            calcTrys++;
            minNumberReducer += 0.4f;
            allTrysForTask++;
        }

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
