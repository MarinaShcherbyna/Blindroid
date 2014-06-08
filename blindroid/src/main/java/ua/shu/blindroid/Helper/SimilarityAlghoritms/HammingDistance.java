package ua.shu.blindroid.Helper.SimilarityAlghoritms;

/**
 * Created by shu on 08.06.14.
 */
public class HammingDistance {

    public static float getHammingDistancePercentage(String s1, String s2)
    {
        return getHammingDistance(s1,s2)/Math.max(s1.length(),s2.length());
    }

    private static int getHammingDistance(String sequence1, String sequence2) {
        char[] s1 = sequence1.toCharArray();
        char[] s2 = sequence2.toCharArray();

        int shorter = Math.min(s1.length, s2.length);
        int longest = Math.max(s1.length, s2.length);

        int result = 0;
        for (int i=0; i<shorter; i++) {
            if (s1[i] == s2[i]) result++;
        }

//        result += longest - shorter;

        return result;
    }

}
