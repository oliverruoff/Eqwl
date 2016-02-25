package hn.bw.de.eu.eqwl.Activities;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import hn.bw.de.eu.eqwl.Helper.ColorHelper;
import hn.bw.de.eu.eqwl.Helper.HSVColorPickerDialog;
import hn.bw.de.eu.eqwl.R;
import hn.bw.de.eu.eqwl.Static.SaveNLoad;
import hn.bw.de.eu.eqwl.Helper.Style;
import hn.bw.de.eu.eqwl.Static.Variables;


public class SettingsActivity extends ActionBarActivity implements View.OnClickListener, HSVColorPickerDialog.OnColorSelectedListener {

    private Style style;
    private static String TAG = "SettingsActivity";
    private SaveNLoad saveNLoad = SaveNLoad.getReference();
    private CheckBox soundCheckBox, equalsSwitchCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        style = new Style(this, (RelativeLayout) findViewById(R.id.settingsLayout));
        setColors();
        initVariables();
        setListeners();
        setCheckBox();
    }

    private void initVariables() {
        soundCheckBox = (CheckBox) findViewById(R.id.soundCheckBox);
        equalsSwitchCheckBox = (CheckBox) findViewById(R.id.equalsSwitchCheckBox);
    }

    private void setCheckBox() {
        if (!Variables.SOUND_ACTIVATED) {
            soundCheckBox.setChecked(false);
        }
        if (Variables.EQUAL_BUTTONS_SWITCHED) {
            equalsSwitchCheckBox.setChecked(true);
        }
    }

    private void setListeners() {
        findViewById(R.id.colorButton).setOnClickListener(this);
    }

    private void setColors() {
        style.setColors(getWindow());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (soundCheckBox.isChecked()) {
            Variables.SOUND_ACTIVATED = true;
            saveNLoad.setSoundActivated(true);
        } else {
            Variables.SOUND_ACTIVATED = false;
            saveNLoad.setSoundActivated(false);
        }
        if (equalsSwitchCheckBox.isChecked()) {
            Variables.EQUAL_BUTTONS_SWITCHED = true;
            saveNLoad.setEqualsButtonsSwitched(true);
        } else {
            Variables.EQUAL_BUTTONS_SWITCHED = false;
            saveNLoad.setEqualsButtonsSwitched(false);
        }
        saveNLoad.saveMainPrefs();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.colorButton) {
            HSVColorPickerDialog cpd = new HSVColorPickerDialog(SettingsActivity.this, 0xFF4488CC, this);
            cpd.setTitle("Pick a color");
            cpd.show();
        }
    }

    @Override
    public void colorSelected(Integer color) {
        Log.d(TAG, "Chosen Color (R | G | B ) selected: " + Color.red(color) + " | " + Color.green(color) + " | " + Color.blue(color) + " INT -> " + color);
        int newRed = Color.red(color), newGreen = Color.green(color), newBlue = Color.blue(color);
        if (newRed > 225) {
            newRed = 225;
        } else if (newRed < 30) {
            newRed = 30;
        }
        if (newGreen > 225) {
            newGreen = 225;
        } else if (newGreen < 30) {
            newGreen = 30;
        }
        if (newBlue > 225) {
            newBlue = 225;
        } else if (newBlue < 30) {
            newBlue = 30;
        }
        color = new ColorHelper().getIntFromColor(newRed, newGreen, newBlue);
        Log.d(TAG, "Taken Color (R | G | B ) selected: " + Color.red(color) + " | " + Color.green(color) + " | " + Color.blue(color) + " INT -> " + color);
        Variables.COLOR = color;
        new Style(this, (RelativeLayout) findViewById(R.id.settingsLayout)).setColors(getWindow());
        saveNLoad.setColor(color);
        saveNLoad.saveMainPrefs();
    }
}
