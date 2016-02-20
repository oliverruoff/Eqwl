package hn.bw.de.eu.eqwl;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;


public class SettingsActivity extends ActionBarActivity implements View.OnClickListener, HSVColorPickerDialog.OnColorSelectedListener {

    private Style style;
    private static String TAG = "SettingsActivity";
    private SaveNLoad saveNLoad = SaveNLoad.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        style = new Style(this, (RelativeLayout) findViewById(R.id.settingsLayout));
        setColors();
        setListeners();
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
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
        if (v.getId() == R.id.colorButton) {
            HSVColorPickerDialog cpd = new HSVColorPickerDialog(SettingsActivity.this, 0xFF4488CC, this);
            cpd.setTitle("Pick a color");
            cpd.show();
        }
    }

    @Override
    public void colorSelected(Integer color) {
        Log.d(TAG, "Color (R | G | B ) selected: " + Color.red(color) + " | " + Color.green(color) + " | " + Color.blue(color) + " INT -> " + color);
        Variables.COLOR = color;
        new Style(this, (RelativeLayout) findViewById(R.id.settingsLayout)).setColors(getWindow());
        saveNLoad.setColor(color);
        saveNLoad.saveMainPrefs();
    }
}
