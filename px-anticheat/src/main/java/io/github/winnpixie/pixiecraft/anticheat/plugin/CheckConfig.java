package io.github.winnpixie.pixiecraft.anticheat.plugin;

import io.github.winnpixie.pixiecraft.commons.config.Linked;

public class CheckConfig {
    private CheckConfig() {
    }

    @Linked("connections.join-delay")
    public static long JOIN_DELAY;

    @Linked("connections.join-delay-warning")
    public static String JOIN_DELAY_WARNING;

    @Linked("chat.message-delay")
    public static long CHAT_DELAY;

    @Linked("chat.message-delay-warning")
    public static String CHAT_DELAY_WARNING;

    @Linked("chat.repeat-message-warning")
    public static String REPEAT_CHAT_WARNING;
}
