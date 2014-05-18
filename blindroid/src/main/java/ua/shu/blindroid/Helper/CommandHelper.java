package ua.shu.blindroid.Helper;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

import org.ispeech.SpeechSynthesisEvent;

import ua.shu.blindroid.Activities.MainActivity;
import ua.shu.blindroid.Entity.Contact;

import static ua.shu.blindroid.Helper.SimilarityHelper.getMaxSimiliraty;

public class CommandHelper {

    public static final String ADD_CONTACT_TEXT = "Додати контакт";
    public static final String CALL_CONTACT_TEXT = "Зателефонувати";
    public enum Functions {ADD_CONTACT, CALL_CONTACT}

    public static void parseCommand(String speechText, MainActivity context)
    {
        Functions currentFunction = getMaxSimiliraty(speechText);
//        SpeechHelper.speechText(context,speechText);

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
                    String resultText = "Зателефонувати - " + contact.name + " за номером : " + contact.phoneNumber + "?";
                    CommandHelper.checkSpeech(resultText, context);
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

        Log.i("123", result.name);
    }

}
