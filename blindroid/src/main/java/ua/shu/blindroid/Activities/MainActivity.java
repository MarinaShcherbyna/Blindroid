package ua.shu.blindroid.Activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ua.shu.blindroid.Entity.Contact;
import ua.shu.blindroid.Helper.CommandHelper;
import ua.shu.blindroid.Helper.PhoneCall.IncomingCallHelper;
import ua.shu.blindroid.Helper.SpeechHelper;
import ua.shu.blindroid.MainApplication;
import ua.shu.blindroid.R;

public class MainActivity extends Activity {

    public static final int START_CODE_SPEECH = 1;
    public static final int CHECK_RESULT_SPEECH = 2;

    public View view;
    public TextView txtText;
    private BroadcastReceiver mReceiver;
    public Contact lastContact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MainApplication mainApplication = (MainApplication) getApplication();
//        mainApplication.setMainActivity(this);

        txtText = (TextView) findViewById(R.id.txtText);

        view = findViewById(R.id.view);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                  txtText.setText("");
                  SpeechHelper.runGoogleSpeechToText(MainActivity.this, START_CODE_SPEECH);
            }
        });


        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("123", "Called");
                IncomingCallHelper.callRecieved(MainActivity.this, context);
            }
        };

        this.registerReceiver(mReceiver, new IntentFilter("android.intent.action.PHONE_STATE"));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {

            ArrayList<String> text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            txtText.setText(text.get(0));

        switch (requestCode) {
            case START_CODE_SPEECH: {
                CommandHelper.parseCommand(text.get(0), MainActivity.this);
            }
            break;

            case CHECK_RESULT_SPEECH:
            {
                CommandHelper.checkAnswer(text.get(0), MainActivity.this);
            }
            break;
        }
        }
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
