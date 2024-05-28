package Utils;

import org.dreambot.api.utilities.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractDigits {
    public static int extractDigits(String input) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        int result = -1;
        if (matcher.find()) {
            try {
                result = Integer.parseInt(matcher.group());
            } catch (NumberFormatException e) {
                Logger.log("Unable to parse digits as an integer: " + e.getMessage());
            }
        } else {
            Logger.log("Unable to get numbers from string: " + input);
        }
        return result;
    }
}
