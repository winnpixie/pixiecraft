package io.github.winnpixie.pixiecraft.social.plugin;

import io.github.winnpixie.pixiecraft.commons.config.Linked;

public class SocialConfig {
    private SocialConfig() {
    }

    @Linked("pricing.chat-color-cost")
    public static double CHAT_COLOR_PRICE;

    @Linked("pricing.nickname-cost")
    public static double NICKNAME_PRICE;
}
