package hn.bw.de.eu.eqwl;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Oliver on 14.02.2016.
 */
public class GameLoop implements View.OnClickListener {

    private RandomNumberHelper rNH = new RandomNumberHelper();
    private CalculationBuilder cB = new CalculationBuilder();
    private Style style;
    private Context context;

    public GameLoop(Context context) {
        this.context = context;
        style = new Style(context,Variables.MAINLAYOUT);
    }

    public void showNewTask() {
        Task t = cB.getRandomTask();
        Variables.CURRENT_TASK = t;
        Variables.CALC_ONE_VIEW.setText(t.calcOne.calcString);
        Variables.CALC_TWO_VIEW.setText(t.calcTwo.calcString);
    }

    public void nextRound() {
        Variables.FILL_TIME_CIRCLE = true;
        Variables.SCORE++;
        Variables.SCORE_VIEW.setText(String.valueOf(Variables.SCORE));
        //Toast.makeText(context, ":)", Toast.LENGTH_SHORT).show();
        style.animateTaskOut();
    }

    public void endGame() {
        Variables.GAME_STARTED = false;
        Variables.TIME_CIRCLE_DP = 0;
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
        }
        if (v.getId() == R.id.againButton) {
            if (!Variables.ANIMATING) {
                againPressed();
            }
        }
        if (v.getId() == R.id.settingsButton) {
            Intent settingsIntent = new Intent(context,SettingsActivity.class);
            context.startActivity(settingsIntent);
        }
        if (v.getId() == R.id.leaderBoardButton) {

        }
    }
}
