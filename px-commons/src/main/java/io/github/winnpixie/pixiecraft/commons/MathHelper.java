package io.github.winnpixie.pixiecraft.commons;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MathHelper {
    private static final Random RANDOM = ThreadLocalRandom.current();

    private MathHelper() {
    }

    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(min, max);
    }

    public static long randomLong(long min, long max) {
        return RANDOM.nextLong(min, max);
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

    public static float randomFloat(float min, float max) {
        return RANDOM.nextFloat(min, max);
    }

    public static boolean isFloat(String value) {
        try {
            float parsed = Float.parseFloat(value);
            return !Float.isNaN(parsed)
                    && !Float.isInfinite(parsed);
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

    public static double randomDouble(double min, double max) {
        return RANDOM.nextDouble(min, max);
    }

    public static boolean isDouble(String value) {
        try {
            double parsed = Double.parseDouble(value);
            return !Double.isNaN(parsed)
                    && !Double.isInfinite(parsed);
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
