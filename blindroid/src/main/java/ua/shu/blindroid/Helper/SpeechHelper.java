package ua.shu.blindroid.Helper;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

public class SpeechHelper {

    private static final String ADD_CONTACT_TEXT = "Додати контакт";
    private static final String CALL_CONTACT_TEXT = "Зателефонувати";

  enum Functions {ADD_CONTACT, CALL_CONTACT}

   public static void parseText(String speechText)
  {
        Functions currentFunction = getMaxSimiliraty(speechText);

        switch (currentFunction) {
            case ADD_CONTACT:
                Log.e("123", ADD_CONTACT_TEXT + " " +speechText);
                break;
            case CALL_CONTACT:
                Log.e("123", CALL_CONTACT_TEXT + " "  + speechText);
                break;
        }
  }

  private static Functions getMaxSimiliraty(String speechText)
    {
        double similarity = 0;
        Functions currFunction = null;

        for (Functions func : Functions.values()) {
            double funcSimilarity = LevenshteinDistance.similarity(speechText, getStringByFunctionEnum(func));
            Log.e("123", getStringByFunctionEnum(func) + "-" + funcSimilarity);
            if (similarity < funcSimilarity) {
                currFunction = func;
                similarity = funcSimilarity;
            }
        }

        return currFunction;
    }

    private static String getStringByFunctionEnum(Functions f)
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
