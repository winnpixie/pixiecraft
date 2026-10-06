package io.github.winnpixie.pixiecraft.announcements.plugin;

import io.github.winnpixie.pixiecraft.commons.config.Linked;

import java.util.List;

public class AnnouncerConfig {
    @Linked("interval")
    public static double INTERVAL;

    @Linked("randomize")
    public static boolean RANDOMIZE;

    @Linked("prefix")
    public static String PREFIX;

    @Linked("suffix")
    public static String SUFFIX;

    @Linked("messages")
    public static List<String> MESSAGES;
}
