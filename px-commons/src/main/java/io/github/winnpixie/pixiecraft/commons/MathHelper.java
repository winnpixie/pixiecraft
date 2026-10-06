package io.github.winnpixie.pixiecraft.commons;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MathHelper {
    private static final Random RANDOM = ThreadLocalRandom.current();

    private MathHelper() {
    }

    /**
     * Returns a pseudo-randomly generated integer value between {@code min} and {@code max}.
     *
     * @param min (inclusive) lower bound for values that can be returned
     * @param max (exclusive) upper bound for values that can be returned
     * @return randomly generated integer value
     */
    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(min, max);
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException _) {
            return false;
        }
    }

    /**
     * Returns a pseudo-randomly generated long value between {@code min} and {@code max}.
     *
     * @param min (inclusive) lower bound for values that can be returned
     * @param max (exclusive) upper bound for values that can be returned
     * @return randomly generated long value
     */
    public static long randomLong(long min, long max) {
        return RANDOM.nextLong(min, max);
    }

    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException _) {
            return false;
        }
    }

    /**
     * Returns a pseudo-randomly generated float value between {@code min} and {@code max}.
     *
     * @param min (inclusive) lower bound for values that can be returned
     * @param max (exclusive) upper bound for values that can be returned
     * @return randomly generated float value
     */
    public static float randomFloat(float min, float max) {
        return RANDOM.nextFloat(min, max);
    }

    public static boolean isFloat(String value) {
        try {
            float parsed = Float.parseFloat(value);
            return !Float.isNaN(parsed)
                    && !Float.isInfinite(parsed);
        } catch (NumberFormatException _) {
            return false;
        }
    }

    /**
     * Returns a pseudo-randomly generated double value between {@code min} and {@code max}.
     *
     * @param min (inclusive) lower bound for values that can be returned
     * @param max (exclusive) upper bound for values that can be returned
     * @return randomly generated double value
     */
    public static double randomDouble(double min, double max) {
        return RANDOM.nextDouble(min, max);
    }

    public static boolean isDouble(String value) {
        try {
            double parsed = Double.parseDouble(value);
            return !Double.isNaN(parsed)
                    && !Double.isInfinite(parsed);
        } catch (NumberFormatException _) {
            return false;
        }
    }
}
