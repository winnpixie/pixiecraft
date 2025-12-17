package io.github.winnpixie.pixiecraft.commons.commands;

import io.github.winnpixie.pixiecraft.commons.CommonWarnings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PlayerCommand<P extends JavaPlugin> extends BaseCommand<P> {
    protected PlayerCommand(String name, P plugin) {
        super(name, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            return execute(player, command, label, args);
        }

        sender.spigot().sendMessage(CommonWarnings.PLAYERS_ONLY);
        return true;
    }

    public abstract boolean execute(Player player, Command command, String label, String[] args);
}
