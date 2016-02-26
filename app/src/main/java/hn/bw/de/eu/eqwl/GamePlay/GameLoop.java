package hn.bw.de.eu.eqwl.GamePlay;

import android.content.Context;
import android.view.View;

import com.google.android.gms.games.Games;

import hn.bw.de.eu.eqwl.Calculations.CalculationBuilder;
import hn.bw.de.eu.eqwl.Calculations.Task;
import hn.bw.de.eu.eqwl.Helper.RandomNumberHelper;
import hn.bw.de.eu.eqwl.Helper.Style;
import hn.bw.de.eu.eqwl.R;
import hn.bw.de.eu.eqwl.Static.Variables;

/**
 * Created by Oliver on 14.02.2016.
 */
public class GameLoop implements View.OnClickListener {

    private RandomNumberHelper rNH = new RandomNumberHelper();
    private CalculationBuilder cB = new CalculationBuilder();
    private Style style;
    private Context context;
    private static String TAG = "GameLoop";

    public GameLoop(Context context) {
        this.context = context;
        style = new Style(context, Variables.MAINLAYOUT);
    }

    public void showNewTask() {
        Task t = cB.getRandomTask();
        Variables.CURRENT_TASK = t;
        Variables.CALC_ONE_VIEW.setText(t.calcOne.calcString); // + " = " + (int) t.calcOne.result
        Variables.CALC_TWO_VIEW.setText(t.calcTwo.calcString); //  +" = " + (int) t.calcTwo.result
//        Log.d(TAG, "result one: " + (int) t.calcOne.result + " | result two: " + (int) t.calcTwo.result);
    }

    public void nextRound() {
        if (Variables.SOUND_ACTIVATED) {
            Variables.SOUND_PLAYER.playRight();
        }
        Variables.FILL_TIME_CIRCLE = true;
        Variables.SCORE++;
        Variables.SCORE_VIEW.setText(String.valueOf(Variables.SCORE));
        //Toast.makeText(context, ":)", Toast.LENGTH_SHORT).show();
        style.animateTaskOut();
    }

    public void endGame() {
        if (Variables.SOUND_ACTIVATED) {
            Variables.SOUND_PLAYER.playWrong();
        }
        Variables.GAME_STARTED = false;
        Variables.TIME_CIRCLE_PX = 0;
        Variables.CALC_ONE_VIEW.setText(Variables.CURRENT_TASK.calcOne.calcString + " = " + (int) Variables.CURRENT_TASK.calcOne.result);
        Variables.CALC_TWO_VIEW.setText(Variables.CURRENT_TASK.calcTwo.calcString + " = " + (int) Variables.CURRENT_TASK.calcTwo.result);

        Variables.AGAIN_BUTTON.setText("\u27F2");
        style.fadeInButtons();
        //Toast.makeText(context, ":(", Toast.LENGTH_SHORT).show();
    }

    public void againPressed() {
        Variables.SCORE = 0;
        Variables.SCORE_VIEW.setText("0");
        Variables.GAME_STARTED = true;
        Variables.FILL_TIME_CIRCLE = true;
        Variables.PLAYED_ALREADY = true;
        style.fadeOutButtons();
        style.animateTaskOut();
    }

    @Override
    public void onClick(View v) {
        //Log.d(TAG, "Clicked: "+v.getId());
        if (Variables.GAME_STARTED && !Variables.ANIMATING) {
            if (!Variables.EQUAL_BUTTONS_SWITCHED) {
                if (v.getId() == R.id.equalButton) {
                    if (Variables.CURRENT_TASK.equal) {
                        nextRound();
                    } else {
                        endGame();
                    }
                } else if (v.getId() == R.id.unequalButton) {
                    if (!Variables.CURRENT_TASK.equal) {
                        nextRound();
                    } else {
                        endGame();
                    }
                }
            } else {
                if (v.getId() == R.id.equalButton) {
                    if (Variables.CURRENT_TASK.equal) {
                        endGame();
                    } else {
                        nextRound();
                    }
                } else if (v.getId() == R.id.unequalButton) {
                    if (!Variables.CURRENT_TASK.equal) {
                        endGame();
                    } else {
                        nextRound();
                    }
                }
            }
        }
        if (v.getId() == R.id.againButton) {
            if (!Variables.ANIMATING) {
                againPressed();
            }
        }
    }
}
