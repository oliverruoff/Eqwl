package hn.bw.de.eu.eqwl.Static;

import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hn.bw.de.eu.eqwl.Calculations.Task;
import hn.bw.de.eu.eqwl.Helper.Style;

/**
 * Created by Oliver on 14.02.2016.
 */
public class Variables {

    public static boolean ANIMATING = false;
    public static RelativeLayout MAINLAYOUT;
    public static TextView CALC_ONE_VIEW;
    public static TextView CALC_TWO_VIEW;
    public static TextView SCORE_VIEW;
    public static Button AGAIN_BUTTON;
    public static Button SETTINGS_BUTTON;
    public static Button LEADER_BOARD_BUTTON;
    public static int SCORE = 0;
    public static boolean GAME_STARTED = false;
    public static Task CURRENT_TASK;
    public static float TIME_CIRCLE_DP = 0;
    public static float DISPLAY_WIDTH;
    public static float DISPLAY_HEIGHT;
    public static Style STYLE;
    public static boolean FILLING_TIME_CIRCLE = false;
    public static boolean FILL_TIME_CIRCLE = false;
    public static boolean PLAYED_ALREADY = false;
    public static int COLOR = -15019115;
    public static RelativeLayout SETTINGS_LAYOUT;
}
