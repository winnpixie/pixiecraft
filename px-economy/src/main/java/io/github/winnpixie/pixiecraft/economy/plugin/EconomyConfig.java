package io.github.winnpixie.pixiecraft.economy.plugin;

import io.github.winnpixie.pixiecraft.commons.config.Linked;

public class EconomyConfig {
    private EconomyConfig() {
    }

    @Linked("currency-name")
    public static String CURRENCY_NAME;
}
