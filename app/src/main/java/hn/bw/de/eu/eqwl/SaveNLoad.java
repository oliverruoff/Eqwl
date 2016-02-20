package hn.bw.de.eu.eqwl;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oliver on 20.02.2016.
 */
public class SaveNLoad {


    private static SaveNLoad self;
    private SharedPreferences sharedMainPreferences;
    private SharedPreferences.Editor mainEditor;
    private int color = -15019115;

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
        sharedMainPreferences.edit().clear();
        mainEditor.clear();
        mainEditor.putString("color",
                String.valueOf(color));

        mainEditor.commit();
    }

    public void loadMainPrefs() {
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

    }

}
