package io.github.winnpixie.pixiecraft.economy.plugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class EconomyWarnings {
    public static final BaseComponent INVALID_ACCOUNT = new ComponentBuilder("Invalid account.")
            .color(ChatColor.RED)
            .build();
    public static final BaseComponent INSUFFICIENT_WALLET_FUNDS = new ComponentBuilder("Insufficient wallet funds.")
            .color(ChatColor.RED)
            .build();
    public static final BaseComponent INSUFFICIENT_FUNDS = new ComponentBuilder("Insufficient funds.")
            .color(ChatColor.RED)
            .build();

    private EconomyWarnings() {
    }
}
