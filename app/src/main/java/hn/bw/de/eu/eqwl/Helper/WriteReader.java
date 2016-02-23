package hn.bw.de.eu.eqwl.Helper;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Oliver on 22.02.2016.
 */
public class WriteReader {

    private Context context;
    private static String TAG = "WriteReader";

    public WriteReader(Context context) {
        this.context = context;
    }

    public  void writeToFile(String body, String fileName)
    {
        FileOutputStream fos = null;

        try {
            final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Eqwl/" );
            Log.d(TAG,"Path for logfile: "+dir.toString());
            if (!dir.exists())
            {
                dir.mkdirs();
            }

            final File myFile = new File(dir, fileName + ".txt");

            if (!myFile.exists())
            {
                myFile.createNewFile();
            }

            fos = new FileOutputStream(myFile);

            fos.write(body.getBytes());
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String readFromFile(String file2Open) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(file2Open);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
