package hn.bw.de.eu.eqwl.Helper;

import android.content.Context;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Oliver on 25.02.2016.
 */
public class LogWriter {

    private static final String TAG = "LogWriter";

    public void writeStackTraceToLog(Context c, Exception e) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        String s = writer.toString();
        new WriteReader(c).writeToFile(s, "Eqwl_Exceptions");
        Log.d(TAG, "Wrote Logfile!");
    }

}
