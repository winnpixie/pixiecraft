package io.github.winnpixie.pixiecraft.economy.plugin;

public class UnitConverter {
    private UnitConverter() {
    }

    public static boolean isUnit(String value) {
        value = value.replace(",", ""); // comma separators don't matter.

        int len = value.length();
        if (len == 0) {
            return false;
        }

        int negIdx = value.indexOf('-');
        if (negIdx > 0
                || negIdx < value.lastIndexOf('-')
                || (negIdx + 1) == len) {
            return false;
        }

        int dotIdx = value.indexOf('.');
        if (dotIdx < value.lastIndexOf('.')
                || (dotIdx + 1) == len) {
            return false;
        }

        for (char c : value.toCharArray()) {
            if ((c < '0' || c > '9')
                    && !(c == '-' || c == '.')) {
                return false;
            }
        }

        return dotIdx == -1 || (dotIdx + 3) >= len;
    }

    public static long fromString(String value) {
        value = value.replace(",", "");

        int dotIdx = value.indexOf('.');
        if (dotIdx == -1) {
            return Long.parseLong(value) * 100L;
        }

        if (dotIdx + 2 == value.length()) {
            value += "0";
        }

        return Long.parseLong(value.replace(".", ""));
    }

    public static String toString(long value) {
        String prefix = "";
        
        if (value < 0) {
            value = -value;
            prefix = "-";
        }

        String str = Long.toString(value);

        if (value < 100) {
            prefix += "0.";

            if (value < 10) {
                prefix += "0";
            }

            return prefix + str;
        }

        int len = str.length();
        return prefix + str.substring(0, len - 2) + "." + str.substring(len - 2);
    }
}
