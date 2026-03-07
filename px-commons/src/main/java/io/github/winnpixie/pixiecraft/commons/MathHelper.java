package io.github.winnpixie.pixiecraft.commons;

public class MathHelper {
    private MathHelper() {
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static int floor(float value) {
        int ival = (int) value;
        return ival < value ? ival : ival - 1;
    }

    public static int ceil(float value) {
        int ival = (int) value;
        return ival > value ? ival : ival + 1;
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static long floor(double value) {
        long lval = (long) value;
        return lval < value ? lval : lval - 1;
    }

    public static long ceil(double value) {
        long lval = (long) value;
        return lval > value ? lval : lval + 1;
    }
}
