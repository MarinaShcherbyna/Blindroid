package ua.shu.blindroid.Helper;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.ispeech.SpeechSynthesis;
import org.ispeech.SpeechSynthesisEvent;
import org.ispeech.error.BusyException;
import org.ispeech.error.InvalidApiKeyException;
import org.ispeech.error.NoNetworkException;


import ua.shu.blindroid.Activities.MainActivity;



public class SpeechHelper {

    public static void runGoogleSpeechToText(MainActivity context,int speechCode) {
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "uk-UA");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "uk-UA");

        try {
            context.startActivityForResult(intent, speechCode);
        } catch (ActivityNotFoundException a) {
            Toast t = Toast.makeText(context,
                    "Ops! Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }



    public static void speechText(final MainActivity context, String text) {
        SpeechSynthesisEvent event = new SpeechSynthesisEvent() {

            public void onPlayFailed(Exception e) {
                Log.e("123", "onPlayFailed");


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Error[TTSActivity]: " + e.toString())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        };

        speechText(context, text, event);

    }

    public static void speechText(final MainActivity context, String text, SpeechSynthesisEvent event) {
            SpeechSynthesis synthesis = null;
            try {
                synthesis = SpeechSynthesis.getInstance(context);
                synthesis.setSpeechSynthesisEvent(event);

                    synthesis.setVoiceType("rurussianmale");
                    synthesis.speak(text);


            } catch (InvalidApiKeyException e) {
                Log.e("123", "Invalid API key\n");
                Toast.makeText(context, "ERROR: Invalid API key", Toast.LENGTH_LONG).show();
            } catch (NoNetworkException e) {
                e.printStackTrace();
                Toast.makeText(context, "NoNetworkException", Toast.LENGTH_LONG).show();
            } catch (BusyException e) {
                Toast.makeText(context, "BusyException", Toast.LENGTH_LONG).show();
            }


    }


}
