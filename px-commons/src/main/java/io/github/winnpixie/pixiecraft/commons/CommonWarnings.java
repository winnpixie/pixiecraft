package io.github.winnpixie.pixiecraft.commons;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class CommonWarnings {
    public static final BaseComponent CANNOT_EXECUTE = new ComponentBuilder("You cannot execute this action.")
            .color(ChatColor.DARK_RED)
            .build();
    public static final BaseComponent INVALID_TARGET = new ComponentBuilder("The requested target was unable to be located.")
            .color(ChatColor.RED)
            .build();
    public static final BaseComponent LACKS_PERMISSION = new ComponentBuilder("You are not permitted to execute this action.")
            .color(ChatColor.RED)
            .build();
    public static final BaseComponent PLAYERS_ONLY = new ComponentBuilder("Only players are permitted to execute this action.")
            .color(ChatColor.YELLOW)
            .build();
    public static final BaseComponent CONSOLE_ONLY = new ComponentBuilder("Only console is permitted to execute this action.")
            .color(ChatColor.YELLOW)
            .build();
    public static final BaseComponent MISSING_PARAMETERS = new ComponentBuilder("One or more parameters are missing to execute this action.")
            .color(ChatColor.RED)
            .build();
    public static final BaseComponent WRONG_ARGUMENT_TYPE = new ComponentBuilder("One or more of the arguments provided were of the wrong type required to execute this action.")
            .color(ChatColor.RED)
            .build();

    private CommonWarnings() {
    }
}
