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

        int dot = -1;
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);

            switch (c) {
                case '-':
                    if (i > 0 || len == 1) {
                        return false;
                    }
                    break;
                case '.':
                    if (dot > -1 || len == 1) {
                        return false;
                    }

                    dot = i;
                    break;
                default:
                    if (c < '0' || c > '9') {
                        return false;
                    }
                    break;
            }
        }

        return dot == -1 || dot + 3 >= len;
    }

    public static long fromString(String value) {
        value = value.replace(",", "");

        int dot = value.indexOf('.');
        if (dot == -1) {
            return Long.parseLong(value) * 100L;
        }

        if (dot + 2 == value.length()) {
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
