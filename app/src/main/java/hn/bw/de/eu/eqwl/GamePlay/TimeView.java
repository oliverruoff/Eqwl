package hn.bw.de.eu.eqwl.GamePlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import hn.bw.de.eu.eqwl.Helper.ColorHelper;
import hn.bw.de.eu.eqwl.Helper.DensityPixelHelper;
import hn.bw.de.eu.eqwl.Helper.Style;
import hn.bw.de.eu.eqwl.Static.Variables;

/**
 * Created by Oliver on 14.02.2016.
 */
public class TimeView extends View {

    private Canvas canvas;
    private Context context;
    private Paint backgroundPaint, foregroundPaint, failPaint;
    private static String TAG = "TimeView";
    private DensityPixelHelper dPH;
    private boolean rendererAtLeastOneFrame = false;
    private float centerX, centerY;
    private int timeToCountDown = 27;
    private ColorHelper cH;
    private int color;

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initVariables();
    }

    private void initVariables() {
        dPH = new DensityPixelHelper(getContext());
        cH = new ColorHelper();
        color = Variables.COLOR;
        backgroundPaint = new Paint();
        backgroundPaint.setColor(cH.getIntFromColor(Color.red(color) + 30, Color.green(color) + 30, Color.blue(color) + 30));
        backgroundPaint.setStyle(Paint.Style.FILL);

        foregroundPaint = new Paint();
        foregroundPaint.setColor(cH.getIntFromColor(Color.red(color) - 20, Color.green(color) - 20, Color.blue(color) - 20));
        foregroundPaint.setStyle(Paint.Style.FILL);

        failPaint = new Paint();
        failPaint.setColor(cH.getIntFromColor(Color.red(color) + 30, Color.green(color) - 20, Color.blue(color) - 20));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
//        Log.d(TAG, "Fill Time Circle: " + Variables.FILL_TIME_CIRCLE +
//                " | CurrentPX: " + Variables.TIME_CIRCLE_PX +
//                " | Max PX: " + Variables.TIME_CIRCLE_PX_MAX +
//                " | Percent: " + Variables.TIME_CIRCLE_PX_1PERCENT +
//                " | Game started: " + Variables.GAME_STARTED);
        if (color != Variables.COLOR) {
            backgroundPaint.setColor(cH.getIntFromColor(Color.red(Variables.COLOR) + 30, Color.green(Variables.COLOR) + 30, Color.blue(Variables.COLOR) + 30));
            foregroundPaint.setColor(cH.getIntFromColor(Color.red(Variables.COLOR) - 20, Color.green(Variables.COLOR) - 20, Color.blue(Variables.COLOR) - 20));
            failPaint.setColor(cH.getIntFromColor(Color.red(Variables.COLOR) + 30, Color.green(Variables.COLOR) - 20, Color.blue(Variables.COLOR) - 20));
        }
        //Log.d(TAG,"GameStarted: "+Variables.GAME_STARTED+" | PlayAgain: "+Variables.FILL_TIME_CIRCLE +" | CircleDP: "+Variables.TIME_CIRCLE_DP);
        canvas.drawCircle(centerX, centerY, Variables.TIME_CIRCLE_PX_MAX, backgroundPaint);
        if (!rendererAtLeastOneFrame) {
            rendererAtLeastOneFrame = true;
            calcCircleParams();
        }
        if (Variables.GAME_STARTED && !Variables.FILL_TIME_CIRCLE) {
            canvas.drawCircle(centerX, centerY, Variables.TIME_CIRCLE_PX, foregroundPaint);
            countDown();
        } else if (Variables.GAME_STARTED && Variables.FILL_TIME_CIRCLE) {
            fillTimeCircle(canvas);
        } else {
            // Waits for game to start
            canvas.drawCircle(centerX, centerY, Variables.TIME_CIRCLE_PX_MAX, failPaint);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            invalidate();
        }
    }

    public void fillTimeCircle(Canvas canvas) {
        if (Variables.TIME_CIRCLE_PX >= Variables.TIME_CIRCLE_PX_MAX) {
            Variables.TIME_CIRCLE_PX = Variables.TIME_CIRCLE_PX_MAX;
            Variables.FILL_TIME_CIRCLE = false;
            canvas.drawCircle(centerX, centerY, Variables.TIME_CIRCLE_PX, foregroundPaint);
        } else {
            Variables.TIME_CIRCLE_PX += Variables.TIME_CIRCLE_PX_1PERCENT * 4;
            canvas.drawCircle(centerX, centerY, Variables.TIME_CIRCLE_PX, foregroundPaint);
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        invalidate();
    }

    private void countDown() {
        try {
            Thread.sleep(timeToCountDown);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Variables.TIME_CIRCLE_PX -= Variables.TIME_CIRCLE_PX_1PERCENT;

        if (Variables.TIME_CIRCLE_PX <= 0) {
            Variables.GAME_STARTED = false;
            Variables.CALC_ONE_VIEW.setText(Variables.CURRENT_TASK.calcOne.calcString + " = " + (int) Variables.CURRENT_TASK.calcOne.result);
            Variables.CALC_TWO_VIEW.setText(Variables.CURRENT_TASK.calcTwo.calcString + " = " + (int) Variables.CURRENT_TASK.calcTwo.result);
            new Style(context, null).fadeInButtons();
        }
        invalidate();
    }

    private void calcCircleParams() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

}
