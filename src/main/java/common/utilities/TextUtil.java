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

}
