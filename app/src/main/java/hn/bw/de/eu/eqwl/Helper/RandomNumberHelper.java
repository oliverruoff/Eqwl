package hn.bw.de.eu.eqwl.Helper;

import java.util.Random;

/**
 * Created by Oliver on 14.02.2016.
 */
public class RandomNumberHelper {

        public int getRandomNumberInt(int min, int max) {
            Random r = new Random();
            int randomNumber = r.nextInt(max - min) + min;
            return randomNumber;
        }

}
