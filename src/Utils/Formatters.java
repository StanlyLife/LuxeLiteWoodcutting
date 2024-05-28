package Utils;

public class Formatters {
    public static String formatElapsedTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long remainingSeconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    public static String roundToK(long number) {
        if (number < 1000) {
            return String.valueOf(number);
        }

        int exponent = (int) (Math.log10(number) / 3);
        double rounded = number / Math.pow(10, exponent * 3);

        if (exponent <= 1) {
            return String.format("%.1f", rounded) + "k";
        } else if (exponent == 2) {
            return String.format("%.1f", rounded) + "M";
        } else if (exponent == 3) {
            return String.format("%.1f", rounded) + "B";
        } else {
            return String.format("%.1f", rounded) + "T";
        }
    }

    public static String formatTime(int seconds) {
        if (seconds < 0) {
            return "Invalid time";
        }

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;

        if (hours >= 100) {
            return String.format("%dh", hours);
        }
        else if (hours > 0 && minutes > 0) {
            return String.format("%dh %dm", hours, minutes);
        } else if (hours > 0) {
            return String.format("%dh", hours);
        } else {
            return String.format("%dm", minutes);
        }
    }

}
