package hn.bw.de.eu.eqwl.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.games.Games;

import hn.bw.de.eu.eqwl.BaseGame.BaseGameActivity;
import hn.bw.de.eu.eqwl.GamePlay.GameLoop;
import hn.bw.de.eu.eqwl.GamePlay.TimeView;
import hn.bw.de.eu.eqwl.Helper.LogWriter;
import hn.bw.de.eu.eqwl.Helper.SoundPlayer;
import hn.bw.de.eu.eqwl.Helper.Style;
import hn.bw.de.eu.eqwl.R;
import hn.bw.de.eu.eqwl.Static.SaveNLoad;
import hn.bw.de.eu.eqwl.Static.Variables;

public class MainActivity extends BaseGameActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private Style style;
    private SaveNLoad saveNLoad = SaveNLoad.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try { In Manifest Permission wieder einfügen (Write external storage)
            setContentView(R.layout.activity_main);

            prepareSaveNLoad();
            initVariables();
            setListener();
            style = new Style(this, Variables.MAINLAYOUT);
            style.setColors(getWindow());
//        } catch (Exception e) {
//            new LogWriter().writeStackTraceToLog(this, e);
//        }
    }

    private void prepareSaveNLoad() {
        SharedPreferences mainPrefs = getSharedPreferences("mainPrefs",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = mainPrefs.edit();
        saveNLoad.setSharedMainPreferences(mainPrefs);
        saveNLoad.setMainEditor(editor);
        saveNLoad.loadMainPrefs();
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        style.setColors(getWindow());
        Variables.CALC_ONE_VIEW.setText("Equal...");
        Variables.CALC_TWO_VIEW.setText("Or Not?");
        Variables.AGAIN_BUTTON.setText("Go");
        style.fadeInButtons();
        style.animateTaskIn();
        if (Variables.SOUND_ACTIVATED) {
            Variables.SOUND_PLAYER = new SoundPlayer(this);
        }
        if (Variables.EQUAL_BUTTONS_SWITCHED) {
            Button equalButton = (Button) findViewById(R.id.equalButton);
            equalButton.setText("≠");
            Button unequalButton = (Button) findViewById(R.id.unequalButton);
            unequalButton.setText("=");
        } else {
            Button equalButton = (Button) findViewById(R.id.equalButton);
            equalButton.setText("=");
            Button unequalButton = (Button) findViewById(R.id.unequalButton);
            unequalButton.setText("≠");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Variables.GAME_STARTED = false;
        Variables.SCORE = 0;
        Variables.SCORE_VIEW.setText("" + Variables.SCORE);
    }

    private void setListener() {
        findViewById(R.id.equalButton).setOnClickListener(new GameLoop(this));
        findViewById(R.id.unequalButton).setOnClickListener(new GameLoop(this));
        Variables.AGAIN_BUTTON.setOnClickListener(new GameLoop(this));
        Variables.SETTINGS_BUTTON.setOnClickListener(this);
        Variables.LEADER_BOARD_BUTTON.setOnClickListener(this);
    }

    private void initVariables() {
        Typeface font = Typeface.createFromAsset(getAssets(), "DejaVuSans.ttf");
        Variables.MAINLAYOUT = (RelativeLayout) findViewById(R.id.mainLayout);
        Variables.CALC_ONE_VIEW = (TextView) findViewById(R.id.calcOne);
        Variables.CALC_TWO_VIEW = (TextView) findViewById(R.id.calcTwo);
        Variables.SCORE_VIEW = (TextView) findViewById(R.id.scoreTextView);
        Variables.AGAIN_BUTTON = (Button) findViewById(R.id.againButton);
        Variables.AGAIN_BUTTON.setTypeface(font);
        Variables.AGAIN_BUTTON.setText("\u27F2");
        Variables.SETTINGS_BUTTON = (Button) findViewById(R.id.settingsButton);
        Variables.SETTINGS_BUTTON.setTypeface(font);
        Variables.SETTINGS_BUTTON.setText("\u2699");
        Variables.LEADER_BOARD_BUTTON = (Button) findViewById(R.id.leaderBoardButton);
        Variables.LEADER_BOARD_BUTTON.setTypeface(font);
        Variables.LEADER_BOARD_BUTTON.setText("\u2605");
        Variables.SETTINGS_LAYOUT = (RelativeLayout) findViewById(R.id.settingsLayout);
        Variables.GAME_STARTED = false;
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();
        Variables.DISPLAY_HEIGHT = height;
        Variables.DISPLAY_WIDTH = width;
        Variables.STYLE = style;

        final TimeView view = (TimeView) findViewById(R.id.timeView);
        final ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    Variables.TIME_CIRCLE_PX_MAX = view.getWidth() / 2f;
                    Variables.TIME_CIRCLE_PX_1PERCENT = view.getWidth() / 100f / 2f;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.settingsButton) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            this.startActivity(settingsIntent);
        }
        if (v.getId() == R.id.leaderBoardButton) {
            Games.Leaderboards.submitScore(getApiClient(),
                    getString(R.string.leaderboard_leaderboard),
                    Variables.SCORE);
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                            getApiClient(), getString(R.string.leaderboard_leaderboard)),
                    1);
        }
    }

    @Override
    public void onSignInFailed() {
    }

    @Override
    public void onSignInSucceeded() {
    }
}
