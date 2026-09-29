package io.github.winnpixie.pixiecraft.social.plugin.utilities;

import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.social.plugin.PxSocialPlugin;

import java.util.regex.Pattern;

public class MessageHelper {
    public static final String UWU_TAG = "uwu_filter";
    public static final String LEET_TAG = "leet_filter";

    private static final Pattern UWU_PATTERN = Pattern.compile("[uor]", Pattern.CASE_INSENSITIVE);
    private static final Pattern LEET_PATTERN = Pattern.compile("[abelostz]", Pattern.CASE_INSENSITIVE);

    private MessageHelper() {
    }

    public static String transform(PDCWrapper<PxSocialPlugin> pdc, String original) {
        String message = original;

        if (pdc.has(UWU_TAG) && pdc.getBoolean(UWU_TAG)) {
            message = transformUwU(message);
        }

        if (pdc.has(LEET_TAG) && pdc.getBoolean(LEET_TAG)) {
            message = transformLeet(message);
        }

        return message;
    }

    private static String transformUwU(String text) {
        return UWU_PATTERN.matcher(text).replaceAll(match -> {
            String letter = match.group();
            return switch (letter) {
                case "o" -> "owo";
                case "O" -> "OwO";
                case "u" -> "uwu";
                case "U" -> "UwU";
                case "r" -> "w";
                case "R" -> "W";
                default -> letter;
            };
        });
    }

    private static String transformLeet(String text) {
        return LEET_PATTERN.matcher(text).replaceAll(match -> {
            String letter = match.group();
            return switch (letter) {
                case "a", "A" -> "4";
                case "b", "B" -> "6";
                case "e", "E" -> "3";
                case "l", "L" -> "1";
                case "o", "O" -> "0";
                case "s", "S" -> "5";
                case "t", "T" -> "7";
                case "z", "Z" -> "2";
                default -> letter;
            };
        });
    }
}
