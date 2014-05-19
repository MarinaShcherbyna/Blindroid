package ua.shu.blindroid.Helper;


public class HammingDistance {

    public static int getDistance(String s1, String s2) {

        // check preconditions
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            throw new IllegalArgumentException();
        }

        // compute hamming distance
        int distance = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                distance++;
            }
        }
        return distance;
    }

}
