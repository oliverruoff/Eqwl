package hn.bw.de.eu.eqwl.Static;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oliver on 20.02.2016.
 */
public class SaveNLoad {


    private static SaveNLoad self;
    private static String TAG = "SaveNLoad";
    private SharedPreferences sharedMainPreferences;
    private SharedPreferences.Editor mainEditor;
    private int color = -15019115;
    private boolean soundActivated = true;
    private boolean equalsButtonsSwitched = false;

    public boolean isEqualsButtonsSwitched() {
        return equalsButtonsSwitched;
    }

    public void setEqualsButtonsSwitched(boolean equalsButtonsSwitched) {
        this.equalsButtonsSwitched = equalsButtonsSwitched;
    }

    public boolean isSoundActivated() {
        return soundActivated;
    }

    public void setSoundActivated(boolean soundActivated) {
        this.soundActivated = soundActivated;
    }

    public static SaveNLoad getReference() {
        if (self == null) {
            self = new SaveNLoad();
        }
        return self;
    }

    private SaveNLoad() {

    }

    public SharedPreferences getSharedMainPreferences() {
        return sharedMainPreferences;
    }

    public void setSharedMainPreferences(SharedPreferences sharedMainPreferences) {
        this.sharedMainPreferences = sharedMainPreferences;
    }

    public SharedPreferences.Editor getMainEditor() {
        return mainEditor;
    }

    public void setMainEditor(SharedPreferences.Editor mainEditor) {
        this.mainEditor = mainEditor;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void saveMainPrefs() {
        Log.d(TAG, "Saving Preferences");
        sharedMainPreferences.edit().clear();
        mainEditor.clear();
        mainEditor.putString("color",
                String.valueOf(color));

        if (soundActivated) {
            mainEditor.putString("soundActivated",
                    "1");
        } else {
            mainEditor.putString("soundActivated",
                    "0");
        }
        if (equalsButtonsSwitched) {
            mainEditor.putString("equalsButtonsSwitched",
                    "1");
        } else {
            mainEditor.putString("equalsButtonsSwitched",
                    "0");
        }
        mainEditor.commit();
    }

    public void loadMainPrefs() {
        Log.d(TAG, "Loading Preferences");
        HashMap<String, String> mainPrefsMap = new HashMap<String, String>();

        for (Map.Entry<String, ?> entry : sharedMainPreferences.getAll().entrySet()) {
            mainPrefsMap.put(entry.getKey().toString(), entry.getValue()
                    .toString());
        }

        if (mainPrefsMap.containsKey("color")) {
            color = Integer.parseInt(mainPrefsMap
                    .get("color"));
            Variables.COLOR = color;
        }
        if (mainPrefsMap.containsKey("soundActivated")) {
            if (mainPrefsMap.get("soundActivated").equals("1")) {
                soundActivated = true;
                Variables.SOUND_ACTIVATED = true;
            } else {
                soundActivated = false;
                Variables.SOUND_ACTIVATED = false;
            }
        }
        if (mainPrefsMap.containsKey("equalsButtonsSwitched")) {
            if (mainPrefsMap.get("equalsButtonsSwitched").equals("1")) {
                equalsButtonsSwitched = true;
                Variables.EQUAL_BUTTONS_SWITCHED = true;
            } else {
                equalsButtonsSwitched = false;
                Variables.EQUAL_BUTTONS_SWITCHED = false;
            }
        }

    }

}
