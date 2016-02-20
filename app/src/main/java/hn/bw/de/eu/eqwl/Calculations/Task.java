package hn.bw.de.eu.eqwl.Calculations;

import hn.bw.de.eu.eqwl.Calculations.Calculation;

/**
 * Created by Oliver on 14.02.2016.
 */
public class Task {

    public Calculation calcOne;
    public Calculation calcTwo;
    public boolean equal = true;

    public Task(Calculation calcOne, Calculation calcTwo, boolean equal) {
        this.calcOne = calcOne;
        this.calcTwo = calcTwo;
        this.equal = equal;
    }
}
