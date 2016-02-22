package hn.bw.de.eu.eqwl.Helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import hn.bw.de.eu.eqwl.R;

/**
 * Created by Oliver on 21.02.2016.
 */
public class SoundPlayer {

    private AudioAttributes audioAttributes;
    private SoundPool soundPool;
    private Context context;
    private int rightSound, wrongSound;

    public SoundPlayer(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
        rightSound = soundPool.load(context, R.raw.right2, 1);
        wrongSound = soundPool.load(context, R.raw.wrong2, 1);
    }

    public void playRight() {
        soundPool.play(rightSound, 1, 1, 0, 0, 1);
    }

    public void playWrong() {
        soundPool.play(wrongSound, 1, 1, 0, 0, 1);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool() {
        audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected void createOldSoundPool() {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

}
