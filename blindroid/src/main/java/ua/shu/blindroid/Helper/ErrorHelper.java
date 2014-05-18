package ua.shu.blindroid.Helper;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Handler;

import java.io.IOException;

import ua.shu.blindroid.R;

public class ErrorHelper {

  public static void playErrorSound(Context context)
    {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.fail);
        mp.start();
    }

    public static void playBeep()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                tg.startTone(ToneGenerator.TONE_CDMA_SOFT_ERROR_LITE);
            }
        }, 1000);


    }

}
