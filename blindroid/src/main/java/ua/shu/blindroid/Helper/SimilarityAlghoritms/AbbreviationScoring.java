package ua.shu.blindroid.Helper.SimilarityAlghoritms;

/**
 * A port of the Quicksilver string ranking algorithm
 *
 * The Quicksilver code is available here.
 * http://code.google.com/p/blacktree-alchemy/
 * http://blacktree-alchemy.googlecode.com/svn/trunk/Crucible/Code/NSString+BLTRRanking.m
 */
public class AbbreviationScoring {



    /**
     * 対象の文字列のスコアを計算する.
     * @param targetStr スコアリング対象となる文字列
     * @param abbreviation この文字列にどの程度マッチしているか計算される
     * @param offset オフセット
     * @return スコア
     */
    public static double getScore(String targetStr, String abbreviation,
                                  int offset) {

//        if (StringUtil.isNull(targetStr)) {
//            return 0.0;
//        }

        if (abbreviation == null || abbreviation.length() == 0) {
            return 0.9;
        }

        if (abbreviation.length() > targetStr.length()) {
            return 0.0;
        }

        for (int i = abbreviation.length(); i > 0; i--) {
            String sub_abbreviation = abbreviation.substring(0, i);
            int index = targetStr.indexOf(sub_abbreviation);

            if (index < 0) {
                continue;
            }

            if (index + abbreviation.length() > targetStr.length() + offset) {
                continue;
            }

            String next_string = targetStr.substring(index
                    + sub_abbreviation.length());
            String next_abbreviation = null;

            if (i >= abbreviation.length()) {
                next_abbreviation = "";

            } else {
                next_abbreviation = abbreviation.substring(i);
            }

            double remaining_score = AbbreviationScoring.getScore(next_string,
                    next_abbreviation, offset + index);

            if (remaining_score > 0) {
                double score = targetStr.length() - next_string.length();

                if (index != 0) {

                    char c = targetStr.charAt(index - 1);
                    if (c == 32 || c == 9) {
                        for (int j = (index - 2); j >= 0; j--) {
                            c = targetStr.charAt(j);
                            score -= ((c == 32 || c == 9) ? 1 : 0.15);
                        }
                    } else {
                        score -= index;
                    }
                }

                score += remaining_score * next_string.length();
                score /= targetStr.length();
                return score;
            }
        }

        return 0.0;
    }
}