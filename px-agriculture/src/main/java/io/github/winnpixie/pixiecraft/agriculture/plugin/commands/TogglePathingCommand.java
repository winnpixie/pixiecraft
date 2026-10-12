package io.github.winnpixie.pixiecraft.agriculture.plugin.commands;

import io.github.winnpixie.pixiecraft.agriculture.plugin.KeyTags;
import io.github.winnpixie.pixiecraft.agriculture.plugin.PxAgriculturePlugin;
import io.github.winnpixie.pixiecraft.commons.PDCWrapper;
import io.github.winnpixie.pixiecraft.commons.commands.PlayerCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class TogglePathingCommand extends PlayerCommand<PxAgriculturePlugin> {
    private final BaseComponent pathingAllowed = new ComponentBuilder("You will now create paths with a shovel")
            .color(ChatColor.GREEN)
            .build();
    private final BaseComponent pathingBlocked = new ComponentBuilder("You will no longer create paths with a shovel")
            .color(ChatColor.RED)
            .build();

    public TogglePathingCommand(PxAgriculturePlugin plugin) {
        super("toggle-pathing", plugin);
    }

    @Override
    public boolean execute(Player player, Command command, String label, String[] args) {
        PDCWrapper<PxAgriculturePlugin> pdc = new PDCWrapper<>(getPlugin(), player);
        boolean pathing = !pdc.has(KeyTags.DIRT_PATHING) || pdc.getBoolean(KeyTags.DIRT_PATHING);

        pdc.setBoolean(KeyTags.DIRT_PATHING, !pathing);
        player.spigot().sendMessage(pathing ? pathingBlocked : pathingAllowed);
        return true;
    }
}
