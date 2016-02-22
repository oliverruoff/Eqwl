package hn.bw.de.eu.eqwl.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import hn.bw.de.eu.eqwl.GamePlay.GameLoop;
import hn.bw.de.eu.eqwl.R;
import hn.bw.de.eu.eqwl.Static.Variables;

/**
 * Created by Oliver on 14.02.2016.
 */
public class Style {

    private static String TAG = "Style";
    private Context context;
    private RelativeLayout mainLayout;
    private int currentapiVersion;
    private ColorHelper cH;

    public Style(Context context, RelativeLayout mainLayout) {
        this.context = context;
        this.mainLayout = mainLayout;
        currentapiVersion = android.os.Build.VERSION.SDK_INT;
        cH = new ColorHelper();
    }

    @SuppressLint("NewApi")
    public Style setBackground() {
//        if (currentapiVersion >= Build.VERSION_CODES.JELLY_BEAN) {
//            GradientDrawable gD = new GradientDrawable(
//                    GradientDrawable.Orientation.TOP_BOTTOM,
//                    new int[]{cH.getIntFromColor(81, 162, 255), cH.getIntFromColor(44, 89, 137)});
//            gD.setCornerRadius(0f);
//            Variables.MAINLAYOUT.setBackground(gD);
//        } else {
        mainLayout.setBackgroundColor(Variables.COLOR);
//        }
        return this;
    }

    @SuppressLint("NewApi")
    public Style setBarColors(Window window) {
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Variables.COLOR);
            window.setNavigationBarColor(Variables.COLOR);
        }
        return this;
    }

    public void setColors(Window window) {
        setBackground();
        setBarColors(window);
        setColorOnViews();
    }

    public void animateTaskOut() {
        Variables.ANIMATING = true;
        Animation animation = new TranslateAnimation(0, Variables.DISPLAY_WIDTH, 0, 0);
        animation.setDuration(500);
        Animation animation2 = new TranslateAnimation(0, -Variables.DISPLAY_WIDTH, 0, 0);
        animation2.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (Variables.PLAYED_ALREADY) {
                    try {
                        new GameLoop(context).showNewTask();
                    }
                    catch (Exception e) {
                        Writer writer = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(writer);
                        e.printStackTrace(printWriter);
                        String s = writer.toString();
                        new WriteReader(context).writeToFile(s,"Eqwl_Exceptions");
                        Log.d(TAG, "Wrote Logfile!");
                    }
                    Variables.ANIMATING = false;
                } else {
                    Variables.CALC_ONE_VIEW.setText("Equal...");
                    Variables.CALC_TWO_VIEW.setText("Or Not? ");
                }
                animateTaskIn();
                Variables.ANIMATING = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Variables.CALC_ONE_VIEW.startAnimation(animation);
        Variables.CALC_TWO_VIEW.startAnimation(animation2);
    }

    public void animateTaskIn() {
        Variables.ANIMATING = true;
        Animation animation = new TranslateAnimation(Variables.DISPLAY_WIDTH, 0, 0, 0);
        animation.setDuration(500);
        Animation animation2 = new TranslateAnimation(-Variables.DISPLAY_WIDTH, 0, 0, 0);
        animation2.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Variables.CALC_ONE_VIEW.startAnimation(animation);
        Variables.CALC_TWO_VIEW.startAnimation(animation2);
    }

    public void fadeInButtons() {
        Variables.ANIMATING = true;
        Animation animation = new TranslateAnimation(0, 0, Variables.DISPLAY_HEIGHT, 0);
        animation.setDuration(500);
        Animation settingsAnimation = new TranslateAnimation(-Variables.DISPLAY_WIDTH, 0, 0, 0);
        settingsAnimation.setDuration(500);
        Animation leaderBoardAnimation = new TranslateAnimation(Variables.DISPLAY_WIDTH, 0, 0, 0);
        leaderBoardAnimation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Variables.ANIMATING = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Variables.LEADER_BOARD_BUTTON.setVisibility(View.VISIBLE);
        Variables.LEADER_BOARD_BUTTON.startAnimation(leaderBoardAnimation);
        Variables.SETTINGS_BUTTON.setVisibility(View.VISIBLE);
        Variables.SETTINGS_BUTTON.startAnimation(settingsAnimation);
        Variables.AGAIN_BUTTON.setVisibility(View.VISIBLE);
        Variables.AGAIN_BUTTON.startAnimation(animation);
    }

    public void fadeOutButtons() {
        Variables.ANIMATING = true;
        Animation animation = new TranslateAnimation(0, 0, 0, Variables.DISPLAY_HEIGHT);
        animation.setDuration(500);
        Animation settingsAnimation = new TranslateAnimation(0, -Variables.DISPLAY_WIDTH, 0, 0);
        settingsAnimation.setDuration(500);
        Animation leaderBoardAnimation = new TranslateAnimation(0, Variables.DISPLAY_WIDTH, 0, 0);
        leaderBoardAnimation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Variables.ANIMATING = false;
                Variables.AGAIN_BUTTON.setVisibility(View.INVISIBLE);
                Variables.LEADER_BOARD_BUTTON.setVisibility(View.INVISIBLE);
                Variables.SETTINGS_BUTTON.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Variables.AGAIN_BUTTON.setVisibility(View.VISIBLE);
        Variables.LEADER_BOARD_BUTTON.setVisibility(View.VISIBLE);
        Variables.SETTINGS_BUTTON.setVisibility(View.VISIBLE);
        Variables.AGAIN_BUTTON.startAnimation(animation);
        Variables.LEADER_BOARD_BUTTON.startAnimation(leaderBoardAnimation);
        Variables.SETTINGS_BUTTON.startAnimation(settingsAnimation);
    }

    public void setColorOnViews() {
        for (int i = 0; i < mainLayout.getChildCount(); i++) {
            Drawable drawableButton = context.getResources().getDrawable(R.drawable.circle);
            drawableButton.setColorFilter(new ColorHelper().getIntFromColor(Color.red(Variables.COLOR) - 20, Color.green(Variables.COLOR) - 20, Color.blue(Variables.COLOR) - 20), PorterDuff.Mode.SRC_IN);
            if (mainLayout.getChildAt(i) instanceof Button && !(mainLayout.getChildAt(i) instanceof CheckBox)) {
                mainLayout.getChildAt(i).setBackground(drawableButton);
            }
        }
    }

}
