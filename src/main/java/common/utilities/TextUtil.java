package common.utilities;

import java.text.DecimalFormat;

public class TextUtil {

    /**
     * Formats the provided number as a silver value (1,000,000s).
     * @param number
     * @return
     */
    public static String formatAsSilver(Number number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number) + "s";
    }

    public static String formatWithCommas(Number number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    public static String toTitleCase(String input) {
        if (input == null) {
            return "";
        }
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
        input = input.toLowerCase();
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

}
