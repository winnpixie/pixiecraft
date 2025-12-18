package io.github.winnpixie.pixiecraft.social.plugin.utilities;

import java.util.regex.Pattern;

public class MessageHelper {
    private static final Pattern UWU_PATTERN = Pattern.compile("[uor]", Pattern.CASE_INSENSITIVE);
    private static final Pattern LEET_PATTERN = Pattern.compile("[abelostz]", Pattern.CASE_INSENSITIVE);

    public static String uwuify(String text) {
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

    public static String hack(String text) {
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

    private MessageHelper() {
    }
}
