package io.github.winnpixie.pixiecraft.core.plugin;

import io.github.winnpixie.pixiecraft.commons.config.Linked;

import java.util.List;

public class CoreConfig {
    private CoreConfig() {
    }

    @Linked("newbies.welcome-message")
    public static List<String> WELCOME_MESSAGES;

    @Linked("newbies.bonus-pay")
    public static double NEWBIE_PAY;

    @Linked("connections.join-message")
    public static String JOIN_MESSAGE;

    @Linked("connections.quit-message")
    public static String QUIT_MESSAGE;

    @Linked("connections.messages-of-the-day")
    public static List<String> MESSAGES_OF_THE_DAY;
}
