package io.github.winnpixie.pixiecraft.commons;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class CommonWarnings {
    public static final BaseComponent CANNOT_EXECUTE = new ComponentBuilder("You cannot execute this action.")
            .color(ChatColor.DARK_RED)
            .build();
    public static final BaseComponent INVALID_TARGET = new ComponentBuilder("Unable to locate target.")
            .color(ChatColor.RED)
            .build();
    public static final BaseComponent PLAYERS_ONLY = new ComponentBuilder("Only players can execute this action.")
            .color(ChatColor.YELLOW)
            .build();
    public static final BaseComponent CONSOLE_ONLY = new ComponentBuilder("Only console can execute this action.")
            .color(ChatColor.YELLOW)
            .build();

    private CommonWarnings() {
    }
}
