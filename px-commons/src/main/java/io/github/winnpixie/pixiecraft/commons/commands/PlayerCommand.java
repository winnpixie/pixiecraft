package io.github.winnpixie.pixiecraft.commons.commands;

import io.github.winnpixie.pixiecraft.commons.WarningMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public abstract class PlayerCommand<P extends JavaPlugin> extends BaseCommand<P> {
    protected PlayerCommand(String name, P plugin) {
        super(name, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            return execute(player, command, label, args);
        }

        sender.spigot().sendMessage(WarningMessages.PLAYERS_ONLY);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            return tabComplete(player, command, label, args);
        }

        return super.onTabComplete(sender, command, label, args);
    }

    public abstract boolean execute(Player player, Command command, String label, String[] args);

    public List<String> tabComplete(Player player, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
