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

    public static String format(String text) {
        return convertCharCodes(convertTags(convertHexColors(text)));
    }

    public static String convertCharCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String convertTags(String text) {
        return TAG_PATTERN.matcher(text).replaceAll(match -> {
            char tag = TAGS_TO_CODES.getOrDefault(match.group(1).toLowerCase(), (char) 0);
            return tag == 0 ? "\u00A7" + tag : match.group();
        });
    }

    public static String convertHexColors(String text) {
        Function<MatchResult, String> transformer = match -> {
            String hex = match.group(1);

            StringBuilder builder = new StringBuilder("\u00A7x");
            for (char c : hex.toCharArray()) {
                builder.append('\u00A7').append(Character.toLowerCase(c));
            }

            return builder.toString();
        };

        text = LEGACY_HEX_PATTERN.matcher(text).replaceAll(transformer);
        return HEX_TAG_PATTERN.matcher(text).replaceAll(transformer);
    }
}
