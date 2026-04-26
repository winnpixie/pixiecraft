package io.github.winnpixie.pixiecraft.agriculture.plugin.commands;

import io.github.winnpixie.pixiecraft.agriculture.plugin.PxAgriculturePlugin;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class ToggleStrippingCommand extends PlayerCommand<PxAgriculturePlugin> {
    private final BaseComponent strippingAllowed = new ComponentBuilder("You will now strip wooden logs with an axe")
            .color(ChatColor.GREEN)
            .build();
    private final BaseComponent strippingBlocked = new ComponentBuilder("You will no longer strip wooden logs with an axe")
            .color(ChatColor.RED)
            .build();

    public ToggleStrippingCommand(PxAgriculturePlugin plugin) {
        super("toggle-stripping", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        PDCWrapper<PxAgriculturePlugin> pdc = new PDCWrapper<>(getPlugin(), player);
        boolean stripping = pdc.has("axe_stripping") && pdc.getBoolean("axe_stripping");

        pdc.setBoolean("axe_stripping", !stripping);
        player.spigot().sendMessage(stripping ? strippingBlocked : strippingAllowed);
        return true;
    }
}
