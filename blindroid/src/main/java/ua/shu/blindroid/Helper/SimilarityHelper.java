package ua.shu.blindroid.Helper;


import android.util.Log;

import java.util.Objects;

import ua.shu.blindroid.Helper.SimilarityAlghoritms.*;

import static ua.shu.blindroid.Helper.CommandHelper.*;

public class SimilarityHelper {

    static String[] namesArray = {"олександр","сергій","володимир","олег",
            "олександр олександрович","олександр сергійович","олександр володимирович","олександр олегович",
            "сергій олександрович","сергій сергійович","сергій володимирович","сергій олегович",
            "володимир олександрович","володимир сергійович","володимир володимирович","володимир олегович",
            "олег олександрович","олег сергійович","олег володимирович","олег олегович",
            "олександр шевчук","сергій шевчук","володимир шевчук","олег шевчук",
            "олександр олександрович шевчук","олександр сергійович шевчук","олександр володимирович шевчук","олександр олегович шевчук",
            "сергій олександрович шевчук","сергій сергійович шевчук","сергій володимирович шевчук","сергій олегович шевчук",
            "володимир олександрович шевчук","володимир сергійович шевчук","володимир володимирович шевчук","володимир олегович шевчук",
            "олег олександрович шевчук","олег сергійович шевчук","олег володимирович шевчук","олег олегович шевчук",
            "олександр шевченко","сергій шевченко","володимир шевченко","олег шевченко",
            "олександр олександрович шевченко","олександр сергійович шевченко","олександр володимирович шевченко","олександр олегович шевченко",
            "сергій олександрович шевченко","сергій сергійович шевченко","сергій володимирович шевченко","сергій олегович шевченко",
            "володимир олександрович шевченко","володимир сергійович шевченко","володимир володимирович шевченко","володимир олегович шевченко",
            "олег олександрович шевченко","олег сергійович шевченко","олег володимирович шевченко","олег олегович шевченко"
    };

    public static Functions getMaxSimiliraty(String speechText)
    {
        double similarity = 0;
        Functions currFunction = null;

        for (Functions func : Functions.values()) {
            String str;
            if (CountWords(func.name) == 1) {
                str = getSplittedWords(speechText)[0];
            } else {
                str = speechText;
            }

            double funcSimilarity = LevenshteinDistance.similarity(str, func.name);
//            Log.e("124", "StringScore similarity '" + str + "' AND '" + func.name + "'  -  " + StringScore.score(str, func.name));
            Log.e("123", str + "-" + funcSimilarity);
            if (similarity < funcSimilarity) {
                currFunction = func;
                similarity = funcSimilarity;
            }
        }

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
//            Log.e("124", "StringScore similarity '" + str + "' AND '" + valueString.name + "'  -  " + StringScore.score(str, valueString.name));
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


    public static void getSimilarityForString(String str)
    {
        double levenstainSimilarity = 0, hemmingSimilarity = 0, jaroSimilarity = 0;
        String levenstainString = null, hemmingString = null, jaroString = null;

        for (String valueString : namesArray) {

            double funcLevenstainSimilarity = LevenshteinDistance.similarity(str, valueString);

            if (levenstainSimilarity < funcLevenstainSimilarity) {
                levenstainString = valueString;
                levenstainSimilarity = funcLevenstainSimilarity;
            }

            double funcHemmingSimilarity = HammingDistance.getHammingDistancePercentage(str, valueString);

            if (hemmingSimilarity < funcHemmingSimilarity) {
                hemmingString = valueString;
                hemmingSimilarity = funcHemmingSimilarity;
            }

            double funcJaroSimilarity = JaroWinklerDistance.getSimilarity(str, valueString);

            if (jaroSimilarity < funcJaroSimilarity) {
                jaroString = valueString;
                jaroSimilarity = funcJaroSimilarity;
            }
        }

        Log.i("123", "Розпізнано слово - " +  str);
        Log.i("123", "Слово за Левенштеном - " +  levenstainString);
        Log.i("123", "Слово за Яро - " +  jaroString);
        Log.i("123", "Слово за Хеммінгом - " +  hemmingString);
    }
}
