package ua.shu.blindroid.Helper;


import android.util.Log;

import java.util.Objects;

import ua.shu.blindroid.Helper.SimilarityAlghoritms.JaroWinklerDistance;
import ua.shu.blindroid.Helper.SimilarityAlghoritms.LevenshteinDistance;
import ua.shu.blindroid.Helper.SimilarityAlghoritms.StringScore;

import static ua.shu.blindroid.Helper.CommandHelper.*;

public class SimilarityHelper {

    public static Functions getMaxSimiliraty(String speechText)
    {
        double similarity = 0, jaroSimilarity = 0;
        Functions currFunction = null,jaroCurrFunc = null;

        for (Functions func : Functions.values()) {
            String str;
            if (CountWords(func.name) == 1) {
                str = getSplittedWords(speechText)[0];
            } else {
                str = speechText;
            }

            double funcSimilarity = LevenshteinDistance.similarity(str, func.name);
            Log.e("124", "StringScore similarity '" + str + "' AND '" + func.name + "'  -  " + StringScore.score(str, func.name));
            Log.e("123", str + "-" + funcSimilarity);
            if (similarity < funcSimilarity) {
                currFunction = func;
                similarity = funcSimilarity;
            }

            double jaroFuncSimilarity = JaroWinklerDistance.getSimilarity(str, func.name);
            if (jaroSimilarity < jaroFuncSimilarity) {
                jaroCurrFunc = func;
                jaroSimilarity = jaroFuncSimilarity;
            }

        }

        Log.e("123", "JaroResult function - " + jaroCurrFunc.name() + "  " + jaroSimilarity);
        Log.e("123", "Levensteint function - " + currFunction.name() + "  " + similarity);

        if (jaroSimilarity > similarity) return jaroCurrFunc;
        if (jaroSimilarity < similarity) return currFunction;



        return currFunction;
    }

    public static AnswerResults getMaxSimilarity(String speechText)
    {
        double similarity = 0;
        AnswerResults resString = null;

        for (AnswerResults valueString : AnswerResults.values()) {
            String str;
            if (CountWords(valueString.name) == 1) {
                str = getSplittedWords(speechText)[0];
            } else {
                str = speechText;
            }

            double funcSimilarity = LevenshteinDistance.similarity(str, valueString.name);
            Log.e("124", "StringScore similarity '" + str + "' AND '" + valueString.name + "'  -  " + StringScore.score(str, valueString.name));
            Log.e("123", str + "-" + funcSimilarity);

            if (similarity < funcSimilarity) {
                resString = valueString;
                similarity = funcSimilarity;
            }
        }

        return resString;
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

//    public static Object getMostSimilarity(Object name, Object[] nameList)
//    {
//
//    }

}
