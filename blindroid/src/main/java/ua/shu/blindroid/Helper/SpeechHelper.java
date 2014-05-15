package ua.shu.blindroid.Helper;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import ua.shu.blindroid.Entity.Contact;
import ua.shu.blindroid.Helper.SimilarityAlghoritms.AbbreviationScoring;
import ua.shu.blindroid.Helper.SimilarityAlghoritms.LevenshteinDistance;
import ua.shu.blindroid.Helper.SimilarityAlghoritms.StringScore;

public class SpeechHelper {

    private static final String ADD_CONTACT_TEXT = "Додати контакт";
    private static final String CALL_CONTACT_TEXT = "Зателефонувати";

  enum Functions {ADD_CONTACT, CALL_CONTACT}

   public static void parseText(String speechText, Context context)
  {
        Functions currentFunction = getMaxSimiliraty(speechText);


        switch (currentFunction) {
            case ADD_CONTACT:
                Log.e("123", ADD_CONTACT_TEXT + " " +speechText);
                break;
            case CALL_CONTACT:
                Log.e("123", CALL_CONTACT_TEXT + " "  + speechText);
                Contact contact = CallContactHelper.getMostSimilarContact(context, speechText);

                if (contact == null) {
                    Log.e("123", "please try again");
                } else {

                    Log.e("123", "Result contact name - " + contact.name + " with phone : " + contact.phoneNumber);
//                    CallContactHelper.callContact(contact, context);
                }


                break;
        }
  }

  private static Functions getMaxSimiliraty(String speechText)
    {
        double similarity = 0;
        Functions currFunction = null;

        for (Functions func : Functions.values()) {
                String str;
                if (CountWords(getStringByFunctionEnum(func)) == 1) {
                    str = getSplittedWords(speechText)[0];
                } else {
                    str = speechText;
                }

                double funcSimilarity = LevenshteinDistance.similarity(str, getStringByFunctionEnum(func));
                Log.e("124", "StringScore similarity '" + str +"' AND '" + getStringByFunctionEnum(func) +"'  -  " + StringScore.score(str, getStringByFunctionEnum(func)));
                Log.e("123", str + "-" + funcSimilarity);
                if (similarity < funcSimilarity) {
                    currFunction = func;
                    similarity = funcSimilarity;
                }
            }

        return currFunction;
    }

    private static int CountWords (String in) {
        String trim = in.trim();
        if (trim.isEmpty()) return 0;
        return trim.split("\\s+").length; //separate string around spaces
    }

    private static String[] getSplittedWords(String speechText)
    {
        return speechText.split(" ");
    }

    public static String getStringByFunctionEnum(Functions f)
    {
        switch (f) {
            case ADD_CONTACT:
                return ADD_CONTACT_TEXT;

            case CALL_CONTACT:
                return CALL_CONTACT_TEXT;
            default:
                return null;
        }
    }

    @Deprecated
    public static void speechToText(Context context, String text)
    {
        final TextToSpeech textToSpeech;
            textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
//                    textToSpeech.setLanguage(Locale.)
                }
            }
        });
    }


}
