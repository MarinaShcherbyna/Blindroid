package ua.shu.blindroid.Helper;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.WindowManager;

import org.ispeech.SpeechSynthesisEvent;

import ua.shu.blindroid.Activities.MainActivity;
import ua.shu.blindroid.Entity.Contact;
import ua.shu.blindroid.R;

import static ua.shu.blindroid.Helper.SimilarityHelper.getMaxSimiliraty;

public class CommandHelper {

    public static final String ADD_CONTACT_TEXT = "Додати контакт";
    public static final String CALL_CONTACT_TEXT = "Зателефонувати";
    public static final String READ_UNREADED_SMS_TEXT = "Прочитати нові повідомлення";
    public static final String BRIGTNESS_ON_TEXT = "Збільшити яскравість єкрану";
    public static final String BRIGTNESS_OFF_TEXT = "Зменшити яскравість єкрану";

    public enum Functions {
        ADD_CONTACT (ADD_CONTACT_TEXT),
        CALL_CONTACT (CALL_CONTACT_TEXT),
        BRIGTNES_ON(BRIGTNESS_ON_TEXT),
        BRIGTNES_OFF(BRIGTNESS_OFF_TEXT),
        UNREADED_SMS (READ_UNREADED_SMS_TEXT);

        public String name;

        private Functions(String s) {
            name = s;
        }

    }

    public static void parseCommand(String speechText, MainActivity context)
    {
        Functions currentFunction = getMaxSimiliraty(speechText);
//        SpeechHelper.speechText(context,speechText);

        if (currentFunction == null) {
            ErrorHelper.nullResult(context);
            return;
        }

        switch (currentFunction) {
            case ADD_CONTACT:
                Log.e("123", ADD_CONTACT_TEXT + " " + speechText);
                break;
            case CALL_CONTACT:
                Log.e("123", CALL_CONTACT_TEXT + " "  + speechText);
                Contact contact = CallContactHelper.getMostSimilarContact(context, speechText);

                if (contact == null) {
//                    ErrorHelper.playErrorSound(context);
//                    ErrorHelper.playBeep();
                    Log.e("123", "please try again");
                } else {

                    Log.e("123", "Result contact name - " + contact.name + " with phone : " + contact.phoneNumber);
//                    CallContactHelper.callContact(contact, context);
                    context.txtText.setText("Result contact name - " + contact.name + " with phone : " + contact.phoneNumber);
                    String resultText = "Зателефонуваты - " + contact.name + "?";
                    context.lastContact = contact;
                    CommandHelper.checkSpeech(resultText, context);
                }
                break;
            case UNREADED_SMS:
                Log.e("123", CALL_CONTACT_TEXT + " "  + speechText);
                SMSHelper.readUnreadedSms(context);
                break;
            case BRIGTNES_ON: {
                context.view.getBackground().setAlpha(0);
                WindowManager.LayoutParams layout = context.getWindow().getAttributes();
                layout.screenBrightness = 1F;
                context.getWindow().setAttributes(layout);
            }
            break;
            case BRIGTNES_OFF:
            {
                context.view.setBackgroundColor(Color.parseColor("#000000"));
                WindowManager.LayoutParams layout = context.getWindow().getAttributes();
                layout.screenBrightness = 0F;
                context.getWindow().setAttributes(layout);
            }
            break;
        }
    }

    private static void checkSpeech(String resultText, final MainActivity context)
    {
        SpeechSynthesisEvent event = new SpeechSynthesisEvent() {
            @Override
            public void onPlaySuccessful() {
                SpeechHelper.runGoogleSpeechToText(context,MainActivity.CHECK_RESULT_SPEECH);
            }
        };

        SpeechHelper.speechText(context, resultText,event);

    }

    public enum AnswerResults {
        ANSWER_YES ("так"),
        ANSWER_NO ("ні");

        public String name;

        private AnswerResults(String s) {
            name = s;
        }

    }

    public static void checkAnswer(String resultText, MainActivity context)
    {
        AnswerResults result = SimilarityHelper.getMaxSimilarity(resultText);

        if (result == AnswerResults.ANSWER_YES) {
            CallContactHelper.callContact(context.lastContact, context);
        } else {
            ErrorHelper.playBeep();
        }
    }

}
