package io.github.winnpixie.pixiecraft.economy.plugin;

import io.github.winnpixie.pixiecraft.commons.config.Linked;

public class EconomyConfig {
    private EconomyConfig() {
    }

    @Linked("currency-name")
    public static String CURRENCY_NAME;

    @Linked("hourly-wage")
    public static double HOURLY_WAGE;

    @Linked("first-join-bonus")
    public static double FIRST_JOIN_BONUS;
}
