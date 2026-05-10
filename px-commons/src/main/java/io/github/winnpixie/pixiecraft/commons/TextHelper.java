package io.github.winnpixie.pixiecraft.commons;

import net.md_5.bungee.api.ChatColor;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class TextHelper {
    private static final Pattern LEGACY_HEX_PATTERN = Pattern.compile("[&\u00A7]#([a-f0-9]{6})", Pattern.CASE_INSENSITIVE);
    private static final Pattern HEX_TAG_PATTERN = Pattern.compile("<#([a-f0-9]{6})>", Pattern.CASE_INSENSITIVE);
    private static final Pattern TAG_PATTERN = Pattern.compile("<([a-z]+)>", Pattern.CASE_INSENSITIVE);
    private static final Map<String, Character> TAGS_TO_CODES;

    static {
        TAGS_TO_CODES = Map.ofEntries(
                Map.entry("black", '0'),
                Map.entry("dark_blue", '1'), Map.entry("darkblue", '1'),
                Map.entry("dark_green", '2'), Map.entry("darkgreen", '2'),
                Map.entry("dark_aqua", '3'), Map.entry("darkaqua", '3'),
                Map.entry("dark_red", '4'), Map.entry("darkred", '4'),
                Map.entry("dark_purple", '5'), Map.entry("darkpurple", '5'),
                Map.entry("gold", '6'),
                Map.entry("gray", '7'), Map.entry("grey", '7'),
                Map.entry("dark_gray", '8'), Map.entry("darkgray", '8'), Map.entry("dark_grey", '8'), Map.entry("darkgrey", '8'),
                Map.entry("blue", '9'),

                Map.entry("green", 'a'),
                Map.entry("aqua", 'b'),
                Map.entry("red", 'c'),
                Map.entry("light_purple", 'd'), Map.entry("lightpurple", 'd'), Map.entry("magenta", 'd'),
                Map.entry("yellow", 'e'),
                Map.entry("white", 'f'),

                Map.entry("obfuscated", 'k'), Map.entry("obscure", 'k'),
                Map.entry("bold", 'l'),
                Map.entry("strikethrough", 'm'),
                Map.entry("underlined", 'n'), Map.entry("underline", 'n'),
                Map.entry("italic", 'o'), Map.entry("emphasized", 'o'),
                Map.entry("reset", 'r'), Map.entry("plain", 'r')
        );
    }

    private TextHelper() {
    }

    public static char getPercentColorCode(int value, int target) {
        return getPercentColorCode((float) value, (float) target);
    }

    public static char getPercentColorCode(long value, long target) {
        return getPercentColorCode((double) value, (double) target);
    }

    public static char getPercentColorCode(float value, float target) {
        float percent = value / target;

        if (percent >= 0.84f) {
            return 'a'; // Green
        } else if (percent >= 0.68f) {
            return '2'; // Dark Green
        } else if (percent >= 0.52f) {
            return 'e'; // Yellow
        } else if (percent >= 0.36f) {
            return '6'; // Gold (Orange)
        } else if (percent >= 0.20f) {
            return 'c'; // Red
        }

        return '4'; // Dark Red
    }

    public static char getPercentColorCode(double value, double target) {
        double percent = value / target;

        if (percent >= 0.84) {
            return 'a'; // Green
        } else if (percent >= 0.68) {
            return '2'; // Dark Green
        } else if (percent >= 0.52) {
            return 'e'; // Yellow
        } else if (percent >= 0.36) {
            return '6'; // Gold (Orange)
        } else if (percent >= 0.20) {
            return 'c'; // Red
        }

        return '4'; // Dark Red
    }

    public static String formatted(String text) {
        return fromStyleCodes(fromStyleTags(fromHexCodes(text)));
    }

    public static String fromStyleCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String fromStyleTags(String text) {
        return TAG_PATTERN.matcher(text).replaceAll(match -> {
            char code = TAGS_TO_CODES.getOrDefault(match.group(1).toLowerCase(), '\0');
            return code == '\0' ? "\u00A7" + code : match.group();
        });
    }

    public static String fromHexCodes(String text) {
        Function<MatchResult, String> transformer = match -> {
            String hex = match.group(1);

            char[] formatted = new char[14];
            for (int i = 0; i < hex.length(); i++) {
                formatted[(i * 2)] = '\u00A7';
                formatted[(i * 2) + 1] = Character.toLowerCase(hex.charAt(i));
            }

            return new String(formatted);
        };

        text = LEGACY_HEX_PATTERN.matcher(text).replaceAll(transformer);
        return HEX_TAG_PATTERN.matcher(text).replaceAll(transformer);
    }
}
